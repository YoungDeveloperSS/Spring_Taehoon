package young.board.comment;

import lombok.Getter;
import young.board.domain.Comment;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String writer;

    public static CommentResponseDto from(Comment comment) {
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.id = comment.getId();
        commentResponseDto.content = comment.getContent();
        commentResponseDto.writer = comment.getWriter();
        return commentResponseDto;
    }
}
