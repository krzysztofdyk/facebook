package facebook.xml;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
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
}

