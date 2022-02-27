package young.board.image;

import lombok.Data;

@Data
public class ImageUpdateRequestDto {
    private Long id;
    //TODO id는 edit할 때 필요함. 그래서 일단 놔둔건데 나중에 분리하기. CreateImageInfoDto, EditImageInfoDto라 써야하나
    private String uri;
    private Integer order;


    public static ImageUpdateRequestDto of(Image image) {
        ImageUpdateRequestDto imageUpdateRequestDto = new ImageUpdateRequestDto();
        imageUpdateRequestDto.id = image.getId();
        imageUpdateRequestDto.uri = image.getUri();
        imageUpdateRequestDto.order = image.getOrder();
        return imageUpdateRequestDto;
    }
}
