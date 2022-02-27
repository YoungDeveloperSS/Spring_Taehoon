package young.board.comment.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentRequestDto {
    private String content;
    private String writer;
}
