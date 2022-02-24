package young.board.image;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ImageRequestDto {
    private String uri;
    private Integer order;
}
