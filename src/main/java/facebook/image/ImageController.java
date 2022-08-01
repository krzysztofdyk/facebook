package facebook.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @GetMapping
    public List<ImageDtoResponse> getAllImages() {
        List<Image> imageList = imageRepository.findAll();
        return imageService.mapToDtoList(imageList);
    }

    @PostMapping("/upload/{accountId}")
    public void uploadFile(@RequestBody MultipartFile image, @PathVariable Long accountId) throws IOException {
        log.info("Image upload started.");
        imageService.uploadImage(image,accountId);
    }

    @GetMapping(value = "/download/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] download(@PathVariable Long imageId) {
        log.info("Image download started.");
        return imageService.downloadImage(imageId);
    }


}
