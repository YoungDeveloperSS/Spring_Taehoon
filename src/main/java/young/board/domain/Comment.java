package young.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;

import static young.board.message.ErrorMessage.NOT_EXIST_COMMENT_ERROR;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private boolean isNotUsing;

//    어차피 id값 있으니까 그 id값 순서대로 보여주면 되는거아냐? 생성순서대로 id 나오니까.
//    @Column(name = "comment_order")
//    private Integer order;
//    나중에 대댓글 같은 기능 나오면 순서가 필요해질듯

    @Builder
    private Comment(Post post, String comment, String writer) {
        validate(post, comment, writer);
        this.post = post;
        this.comment = comment;
        this.writer = writer;
        this.isNotUsing = false;
    }

    private void validate(Post post, String comment, String writer) {
        if (post == null) {
            throw new IllegalArgumentException("해당되는 게시물을 찾을 수 없습니다.");
        }
        if (!StringUtils.hasText(comment)) {
            throw new IllegalArgumentException("댓글 내용이 입력되지 않았습니다.");
        }
        if (!StringUtils.hasText(writer)) {
            throw new IllegalArgumentException("댓글 작성자가 입력되지 않았습니다.");
        }
    }

    public void update(String commentContent, String writer) {
        this.comment = commentContent;
        this.writer = writer;
    }

    public void delete() {
        if (isNotUsing) {
            throw new IllegalArgumentException(NOT_EXIST_COMMENT_ERROR);
        }
        isNotUsing = true;
    }
}
