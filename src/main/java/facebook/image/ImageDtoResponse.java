package facebook.image;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDtoResponse {
    private long id;
    private String imageName;
    private byte[] imageByte;
}
