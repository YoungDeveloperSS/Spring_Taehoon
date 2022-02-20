package young.board.repository.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import young.board.comment.repository.CommentRepository;
import young.board.domain.Category;
import young.board.domain.Comment;
import young.board.domain.Post;
import young.board.post.repository.PostRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class H2CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Autowired PostRepository postRepository;

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
        postRepository.save(post1);

        post2 = Post.createPost()
                .category(Category.QUESTION)
                .title("제목2")
                .writer("작성자2")
                .content("내용2~~~~")
                .build();
        postRepository.save(post2);

        comment1 = Comment.builder()
                .post(post1)
                .comment("댓글1")
                .writer("댓작성자1")
                .build();
        commentRepository.save(comment1);

        comment2 = Comment.builder()
                .post(post2)
                .comment("댓글2")
                .writer("댓작성자2")
                .build();
        commentRepository.save(comment2);

        comment3 = Comment.builder()
                .post(post1)
                .comment("댓글3")
                .writer("댓작성자3")
                .build();
        commentRepository.save(comment3);
    }

    @Test
    void 댓글_생성_정상() {
        Comment comment = commentRepository.findOne(comment1.getId()).get();

        assertThat(comment.getId()).isEqualTo(comment1.getId());
        assertThat(comment.getContent()).isEqualTo(comment1.getContent());
        assertThat(comment.getPost()).isEqualTo(comment1.getPost());
        assertThat(comment.getWriter()).isEqualTo(comment1.getWriter());
    }

    @Test
    void 댓글_전체_조회_정상() {
        List<Comment> allUsingPostId = commentRepository.findAllUsingPostId(post1.getId());
        assertThat(allUsingPostId.size()).isEqualTo(2);
        //취소도 테스트 해봐야하는데 -> 서비스에서 테스트하자.
    }

}