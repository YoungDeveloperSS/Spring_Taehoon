package young.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.board.domain.Category;
import young.board.domain.Like;
import young.board.domain.Post;
import young.board.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long save(String title, String writer, String content, Category category) {
        Post post = Post.createPost(title, category, writer, content);
        return postRepository.save(post);
    }

    public Post findPost(Long postId) {
        return validatePostExist(postId);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public Long update(Long postId, String title, String writer, String content, Category category) {
        Post post = validatePostExist(postId);
        post.update(title, writer, content, category);
        return postId; // 테스트를 위해 Long이라도 반환하는게 좋을거같음. void보다.
    }

    private Post validatePostExist(Long postId) {
        Optional<Post> parsingPost = postRepository.findOne(postId);
        if (parsingPost.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 게시글입니다.");
        }
        Post post = parsingPost.get();
        return post;
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = validatePostExist(postId);
        //권한 체크
        postRepository.delete(post);
    }

    @Transactional
    public Like likePost(Long postId) {
        Post post = validatePostExist(postId);
        return post.like();
    }

    @Transactional
    public Like disLikePost(Long postId) {
        Post post = validatePostExist(postId);
        return post.disLike();
    }
}
