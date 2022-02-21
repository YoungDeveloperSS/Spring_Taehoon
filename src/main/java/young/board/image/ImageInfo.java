package young.board.image;

import lombok.Getter;

import java.net.URI;

@Getter
public class ImageInfo {
    private Long id;
    private URI uri;
    private Integer order;
}
