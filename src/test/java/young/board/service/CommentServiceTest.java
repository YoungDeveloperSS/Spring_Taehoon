package young.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import young.board.comment.CommentResponseDto;
import young.board.comment.CommentService;
import young.board.domain.Category;
import young.board.domain.Comment;
import young.board.post.service.PostService;
import young.board.comment.repository.CommentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired CommentRepository commentRepository;
    @Autowired
    PostService postService;

    Long savedPostId;
    Long savedPostId2;

    @BeforeEach
    void beforeEach() {
        savedPostId = postService.save("제목", "작성자", "내용", Category.CHAT);
        savedPostId2 = postService.save("제목2", "작성자2", "내용2", Category.CHAT);
    }
    @Test
    void 댓글_작성() {
        Long commentId = commentService.comment(savedPostId, "댓글내용", "댓작성자");
        Comment comment = commentRepository.findOne(commentId).get();
        assertThat(comment.getId()).isEqualTo(commentId);
    }

    @Test
    void 게시물_댓글_전체_조회() {
        Long commentId1 = commentService.comment(savedPostId, "댓글내용", "댓작성자");
        Long commentId2 = commentService.comment(savedPostId2, "댓글내용2", "댓작성자");
        Long commentId3 = commentService.comment(savedPostId, "댓글내용2", "댓작성자");
        Long commentId4 = commentService.comment(savedPostId, "댓글내용4", "댓작성자");

        assertThat(commentService.inqueryCommentsOnPost(savedPostId).size()).isEqualTo(3);
    }

    @Test
    void 댓글_수정_정상() {
        Long commentId1 = commentService.comment(savedPostId, "댓글내용", "댓작성자");
        commentService.update(commentId1, "변경내용1", "작성자도변경");

        Comment comment = commentRepository.findOne(commentId1).get();
        assertThat(comment.getContent()).isEqualTo("변경내용1");
        assertThat(comment.getWriter()).isEqualTo("작성자도변경");
    }

    @Test
    void 댓글_삭제_정상() {
        Long commentId1 = commentService.comment(savedPostId, "댓글내용", "댓작성자");
        commentService.delete(commentId1);

        List<CommentResponseDto> comments = commentService.inqueryCommentsOnPost(commentId1);
        assertThat(comments.size()).isEqualTo(0);
    }

    @Test
    void 삭제된_댓글_삭제_예외() {
        Long commentId1 = commentService.comment(savedPostId, "댓글내용", "댓작성자");
        commentService.delete(commentId1);

        assertThatThrownBy(() -> commentService.delete(commentId1)).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("삭제되었거나 존재하지 않는 댓글입니다.");
    }

}