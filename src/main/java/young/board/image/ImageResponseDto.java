package young.board.image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResponseDto {
    private Long id;
    //TODO id는 edit할 때 필요함. 그래서 일단 놔둔건데 나중에 분리하기. CreateImageInfoDto, EditImageInfoDto라 써야하나
    private String uri;
    private Integer order;


    public static ImageResponseDto of(Image image) {
        ImageResponseDto imageResponseDto = new ImageResponseDto();
        imageResponseDto.id = imageResponseDto.getId();
        imageResponseDto.uri = image.getUri();
        imageResponseDto.order = image.getOrder();
        return imageResponseDto;
    }
}
