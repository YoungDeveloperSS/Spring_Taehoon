package young.board.image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageInfo {
    private Long id;
    //TODO id는 edit할 때 필요함. 그래서 일단 놔둔건데 나중에 분리하기. CreateImageInfoDto, EditImageInfoDto라 써야하나
    private String uri; //굳이 URI 타입이 아니라 string이어도 상관없지않을까?
    private Integer order;
}
