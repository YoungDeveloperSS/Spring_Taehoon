package young.board.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.board.image.repository.ImageRepository;
import young.board.post.Category;
import young.board.post.Post;
import young.board.post.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static young.board.message.ErrorMessage.NOT_EXIST_POST_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    //TODO 이미지 삭제가 여기서 이뤄져도 괜찮나?
    // 근데 순서상 게시물 삭제 -> 연관되어 모든 images들 삭제면 여기 있어야 할지도. 이거 연관관계 관련된거일까. 공부 필요할거같음

    @Transactional
    public Long save(String title, String writer, String content, Category category) {
        Post post = Post.createPost()
                .title(title)
                .category(category)
                .writer(writer)
                .content(content)
                .build();
        return postRepository.save(post);
    }

    public PostResponseServiceDto findPost(Long postId) {
        Post post = validatePostExist(postId);
        return PostResponseServiceDto.from(post);
    }

    public List<PostResponseServiceDto> findAll(int page) {
        List<Post> posts = postRepository.findAll(page);
        return posts.stream()
                .map(post -> PostResponseServiceDto.from(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long postId, String title, String writer, String content, Category category) {
        Post post = validatePostExist(postId);
        post.update(title, writer, content, category); //todo 이거도 빌더패턴 같은거로 바꿔주는게 보기 좋으려나?
        return postId; // 테스트를 위해 Long이라도 반환하는게 좋을거같음. void보다.
    }

    private Post validatePostExist(Long postId) {
        Optional<Post> parsingPost = postRepository.findOne(postId);
        if (parsingPost.isEmpty()) {
            throw new IllegalStateException(NOT_EXIST_POST_ERROR);
        }
        Post post = parsingPost.get();
        if (post.isNotUsing()) { //todo 왜 메서드가 자동으로 만들어지는지 알아보기.
            throw new IllegalStateException(NOT_EXIST_POST_ERROR);
        }
        return post;
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = validatePostExist(postId); //db에 해당 id가 있나?
        //TODO 로그인 구현한 이후에 권한 체크 들어가야 함.
        post.delete();
        imageRepository.deleteAllByPost(post);
    }
}
