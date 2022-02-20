package young.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import young.board.domain.Category;
import young.board.domain.Post;
import young.board.post.repository.PostRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class H2PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Test
    void 게시글_저장() { //게시글 조회도 같이 테스트됨.
        Post post = Post.createPost()
                .title("제목")
                .category(Category.CHAT)
                .writer("작성자")
                .content("내용")
                .build();
//        Post post = Post.createPost("제목", Category.CHAT, "작성자", "내용~~~~");
        Long saveId = postRepository.save(post);

        Post savedPost = postRepository.findOne(saveId).get();
        assertThat(savedPost.getId()).isEqualTo(post.getId());
        assertThat(savedPost.getContent()).isEqualTo(post.getContent());
        assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(savedPost.getCreateDate()).isEqualTo(post.getCreateDate());
        assertThat(savedPost.getWriter()).isEqualTo(post.getWriter());
    }

//    @Test
//    void 게시글_삭제() {
//        Post targetPost = Post.createPost()
//                .title("제목")
//                .category(Category.CHAT)
//                .writer("작성자")
//                .content("내용")
//                .build();
//        Long saveId = postRepository.save(targetPost);
//        Post savedPost = postRepository.findOne(saveId).get();
//        postRepository.delete(savedPost);
//
//        List<Post> allPosts = postRepository.findAll();
//        assertThat(allPosts.stream().anyMatch(post -> post.getId() == saveId)).isFalse();
//    }

    @Test
    void 존재_않는_게시글_조회() {
        assertThat(postRepository.findOne(100000L)).isEmpty();
    }

    @Test
    void 게시글_전체_조회() {
        Post post1 = Post.createPost()
                .title("제목")
                .category(Category.CHAT)
                .writer("작성자")
                .content("내용")
                .build();
        postRepository.save(post1);
        Post post2 = Post.createPost()
                .title("제목2")
                .category(Category.QUESTION)
                .writer("작성자")
                .content("내용~~~~")
                .build();
        postRepository.save(post2);

        assertThat(postRepository.findAll().size()).isEqualTo(2);
    }

}