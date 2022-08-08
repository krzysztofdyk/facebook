package facebook.xml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Data
@Transactional
public class XmlService {

    private final String DOWNLOAD_PATH = "D:\\";

    private final XmlRepository xmlRepository;

    public void uploadXml(MultipartFile xmlFile) throws IOException {
        if (xmlFile.isEmpty()) {
            throw new IllegalArgumentException("Please select a XML to save");
        }
        Xml xml = Xml.builder()
                .xmlName(xmlFile.getOriginalFilename())
                .xmlByte(xmlFile.getBytes())
                .build();
        xmlRepository.save(xml);
        log.info("XML upload finished.");
    }

    private XmlDtoResponse mapDto(Xml xml){
        return XmlDtoResponse.builder()
                .id(xml.getId())
                .name(xml.getXmlName())
                .build();
    }
    public List<XmlDtoResponse> mapToDtoList(List<Xml> xmlList){
        return xmlList.stream().map(this::mapDto).collect(Collectors.toList());
    }

    @SneakyThrows
    public void downloadXml(Long xmlId) {
        Xml xml = xmlRepository.getById(xmlId);
        String shortName = xml.getXmlName().substring(0, xml.getXmlName().length()-3);
        File file = new File(DOWNLOAD_PATH+shortName+"xml");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(xml.getXmlByte());
        log.info("XML download finished.");
    }


    public void  getTheRoot(Long xmlId){
        try {
            Xml xml = xmlRepository.getById(xmlId);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(xml.getXmlByte());
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(String.valueOf(outputStream));
            Element root = document.getRootElement();
            List<String> list = new ArrayList<>();
            for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
                Element element = it.next();
                list.add(element.getName());
                System.out.println(element.getName());
            }

        }catch(Exception e){
            System.out.println("There sth wrong " + e.getMessage());
        }

    }

    public String mapToJson(Long xmlId) throws IOException{
        Xml xml = xmlRepository.getById(xmlId);
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode node = xmlMapper.readTree(xml.getXmlByte());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(node);

    }
}

