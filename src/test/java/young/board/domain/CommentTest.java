package young.board.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentTest {
    // id같은 경우 Spring이 만들어주는데 여기에 어떻게 넣지?
    Post post1;
    Post post2;
    Comment comment1;
    Comment comment2;
    Comment comment3;

    @BeforeEach
    void beforeEach() {
        post1 = Post.createPost()
                .category(Category.CHAT)
                .title("제목1")
                .writer("작성자")
                .content("내용~~~~")
                .build();
        post2 = Post.createPost()
                .category(Category.QUESTION)
                .title("제목2")
                .writer("작성자2")
                .content("내용2~~~~")
                .build();
        comment1 = Comment.builder()
                .post(post1)
                .comment("댓글1")
                .writer("댓작성자1")
                .build();
        comment2 = Comment.builder()
                .post(post2)
                .comment("댓글2")
                .writer("댓작성자2")
                .build();
        comment3 = Comment.builder()
                .post(post1)
                .comment("댓글3")
                .writer("댓작성자3")
                .build();
    }

    @Test
    void 댓글_생성_정상() {
        assertThat(comment1.getPost()).isEqualTo(post1);
        assertThat(comment1.getContent()).isEqualTo("댓글1");
        assertThat(comment1.getWriter()).isEqualTo("댓작성자1");
    }

    @Test
    void 댓글_작성_게시물_안넣음_예외() {
        assertThatThrownBy(() -> Comment.builder()
                .post(null)
                .comment("댓글3")
                .writer("댓작성자3")
                .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당되는 게시물을 찾을 수 없습니다.");
    }

    @Test
    void 댓글_작성_내용_안넣음_예외() {
        assertThatThrownBy(() -> Comment.builder()
                .post(post1)
                .comment("")
                .writer("댓작성자3")
                .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("댓글 내용이 입력되지 않았습니다.");
    }

    @Test
    void 댓글_작성_작성자_안넣음_예외() {
        assertThatThrownBy(() -> Comment.builder()
                .post(post1)
                .comment("댓글3")
                .writer("")
                .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("댓글 작성자가 입력되지 않았습니다.");
    }


}