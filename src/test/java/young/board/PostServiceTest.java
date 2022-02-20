package young.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import young.board.domain.Category;
import young.board.domain.Post;
import young.board.post.PostService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    PostService postService;
    Long savedId1;
    Long savedId2;
    Long savedId3;
    Long savedId4;

    @BeforeEach
    void beforeEach() {
        savedId1 = postService.save("title", "writer", "content~~~~", Category.NOTICE);
        savedId2 = postService.save("title2", "writer2", "content2~~~~", Category.CHAT);
        savedId3 = postService.save("title3", "writer3", "content3~~~~", Category.QUESTION);
        savedId4 = postService.save("title4", "writer4", "content4~~~~", Category.QUESTION);
    }

    @Test
    void 게시글_저장_조회() { //하나의 테스트 하나의 기능인데, 한번에 두 개가 테스트 되는 경우에는 어떻게 만들어야 하지?
        Post findPost = postService.findPost(savedId1);
        assertThat(findPost.getTitle()).isEqualTo("title");
        assertThat(findPost.getWriter()).isEqualTo("writer");
        assertThat(findPost.getContent()).isEqualTo("content~~~~");
        assertThat(findPost.getCategory()).isEqualTo(Category.NOTICE);
    }

    @Test
    void 존재하지_않는_게시글_조회_예외() {
        assertThatThrownBy(() -> postService.findPost(10000000L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("존재하지 않는 게시글입니다.");
    }

    @Test
    void 게시글_전체_조회() {
        assertThat(postService.findAll().size()).isEqualTo(4);
    }

    @Test
    void 게시글_수정() {
        Post findPost = postService.findPost(savedId3);
        Long updateId = postService.update(findPost.getId(), "제목변경", "작성자변경", null, Category.NOTICE);

        Post updatePost = postService.findPost(updateId);

        assertThat(updatePost.getId()).isEqualTo(findPost.getId());
        assertThat(updatePost.getTitle()).isEqualTo("제목변경");
        assertThat(updatePost.getWriter()).isEqualTo("작성자변경");
        assertThat(updatePost.getContent()).isNull();
        assertThat(updatePost.getCategory()).isEqualTo(Category.NOTICE);
    }

    @Test
    void 게시글_삭제() {
        postService.deletePost(savedId3);
        assertThatThrownBy(() -> postService.findPost(savedId3))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("존재하지 않는 게시글입니다.");
    }

    @Test
    void 존재_않는_게시글_삭제_예외() {
        postService.deletePost(savedId3);
        assertThatThrownBy(() -> postService.deletePost(savedId3))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("존재하지 않는 게시글입니다.");
    }

//    @Test
//    void 좋아요_누르기() {
//        postService.likePost(savedId1);
//        postService.likePost(savedId1);
//
//        Post post = postService.findPost(savedId1);
//        Likes likeNumber = post.getLikeNumber();
//
//        assertThat(likeNumber.getLikeNumber()).isEqualTo(2);
//    }
//
//    @Test
//    void 싫어요_누르기() {
//        postService.likePost(savedId1);
//        postService.disLikePost(savedId1);
//        postService.disLikePost(savedId1);
//
//        Post post = postService.findPost(savedId1);
//        Likes likeNumber = post.getLikeNumber();
//
//        assertThat(likeNumber.getLikeNumber()).isEqualTo(-1);
//    }


}
