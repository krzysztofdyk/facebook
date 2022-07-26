package facebook.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public void uploadFile(@RequestBody MultipartFile image) throws IOException {
        log.info("Image upload started.");
        imageService.uploadImage(image);
    }

    @PostMapping(value = "/download/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] download(@PathVariable Long id) {
        log.info("Image download started.");
        return imageService.downloadImage(id);

    }
}
