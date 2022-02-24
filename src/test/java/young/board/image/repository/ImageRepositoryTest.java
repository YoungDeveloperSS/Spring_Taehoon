package young.board.image.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import young.board.image.Image;
import young.board.post.Category;
import young.board.post.Post;
import young.board.post.repository.PostRepository;
import young.board.post.service.PostService;
import young.board.user.UserRepository;
import young.board.user.Users;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageRepositoryTest {
    @Autowired ImageRepository imageRepository;

    @Autowired PostService postService;
    @Autowired PostRepository postRepository;

    @Autowired UserRepository userRepository;

    @Transactional
    @Test
    void 이미지들_post로_조회() throws URISyntaxException {
        Long postId = postService.save("제목", "작성자", "ㅇㅇㅇ", Category.CHAT);
        postService.findPost(postId);
        Post post = postRepository.findOne(postId).get();

        Long savedId = userRepository.save(new Users());
        Users users = userRepository.findOne(savedId).get();

        Image image2 = Image.builder()
                .post(post)
                .user(users)
                .uri(new URI("http://www.naver.com"))
                .order(2)
                .build();
        imageRepository.save(image2);

        Image image1 = Image.builder()
                .post(post)
                .user(users)
                .uri(new URI("http://www.naver.com"))
                .order(1)
                .build();
        imageRepository.save(image1);

        Image image3 = Image.builder()
                .post(post)
                .user(users)
                .uri(new URI("http://www.naver.com"))
                .order(3)
                .build();
        imageRepository.save(image3);

//        imageRepository.deleteAllByPost(post); -> 필요 없는 메서드라 삭제해줌. 이 작업은 서비스 단계에서 하는거.
//        imageRepository.delete(image1);
//        List<Image> images = imageRepository.findAll();
        List<Image> images = imageRepository.findAllByPost(post);
        Assertions.assertThat(images.size()).isEqualTo(3);
    }

}