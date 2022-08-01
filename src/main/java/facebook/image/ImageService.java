package facebook.image;

import ch.qos.logback.core.util.FileSize;
import facebook.account.Account;
import facebook.account.AccountRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Data
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;
    private final AccountRepository accountRepository;

    public void uploadImage(MultipartFile imageFile,Long accountId) throws IOException {
        Account account = accountRepository.getById(accountId);
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("Please select a image to save");
        }
        if (imageFile.getSize() >= FileSize.MB_COEFFICIENT * 5){
            throw new IllegalArgumentException("The image size cannot exceed 5 Mb");
        }

        Image image = Image.builder()
                .imageName(imageFile.getOriginalFilename())
                .imageByte(imageFile.getBytes())
                .account(account)
                .build();
        imageRepository.save(image);
        account.setImage(image);
        accountRepository.save(account);
        log.info("Image upload finished.");
    }

    @Transactional
    public byte[] downloadImage(Long imageId) {
        Image image = imageRepository.getById(imageId);
        log.info("Image download finished.");
        return image.getImageByte();
    }

    private ImageDtoResponse mapDto(Image image){
        return ImageDtoResponse.builder()
                .id(image.getId())
                .accountId(image.getAccount().getId())
                .imageName(image.getImageName())
                .build();
    }

    public List<ImageDtoResponse> mapToDtoList(List<Image> imageList){
        return imageList.stream().map(this::mapDto).collect(Collectors.toList());
    }

}
