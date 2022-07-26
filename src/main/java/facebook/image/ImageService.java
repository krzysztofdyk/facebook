package facebook.image;

import ch.qos.logback.core.util.FileSize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@Data
public class ImageService {

    private final ImageRepository imageRepository;

    public void uploadImage(MultipartFile imageFile) throws IOException {

        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("Please select a image to save");
        }
        if (imageFile.getSize() >= FileSize.MB_COEFFICIENT * 5){
            throw new IllegalArgumentException("The image size cannot exceed 5 Mb");
        }

        Image image = Image.builder()
                .imageName(imageFile.getOriginalFilename())
                .imageByte(imageFile.getBytes())
                .build();
        imageRepository.save(image);
        log.info("Image upload finished.");
    }

    @Transactional
    public byte[] downloadImage(Long id) {
        Image image = imageRepository.getById(id);
        log.info("Image download finished.");
        mapDto(image);
        return image.getImageByte();
    }

    private ImageDtoResponse mapDto(Image image){
        return ImageDtoResponse.builder()
                .id(image.getId())
                .imageName(image.getImageName())
                .imageByte(image.getImageByte())
                .build();
    }

}
