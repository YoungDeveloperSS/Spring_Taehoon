package young.board.image;

import lombok.Getter;

import java.net.URI;

@Getter
public class ImageInfo {
    private Long id;
    //TODO id는 edit할 때 필요함. 그래서 일단 놔둔건데 나중에 분리하기. CreateImageInfoDto, EditImageInfoDto라 써야하나
    private URI uri;
    private Integer order;
}
