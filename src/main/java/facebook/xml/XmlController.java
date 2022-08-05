package facebook.xml;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/xml")
@Data
public class XmlController {

    private final XmlRepository xmlRepository;
    private final XmlService xmlService;

    @GetMapping
    public List<XmlDtoResponse> getAllXml() {
        List<Xml> xmlList = xmlRepository.findAll();
        return xmlService.mapToDtoList(xmlList);
    }

    @PostMapping("/upload")
    public void uploadXml(@RequestBody MultipartFile xml) throws IOException {
        log.info("XML upload started.");
        xmlService.uploadXml(xml);
    }
    @GetMapping("/download/{xmlId}")
    public void downloadXml(@PathVariable Long xmlId) {
        log.info("XML download started.");
        xmlService.downloadXml(xmlId);
    }

}
