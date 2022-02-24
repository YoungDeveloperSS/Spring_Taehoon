package young.board.comment;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentRequestDto {
    private String content;
    private String writer;
}
