package young.board.post.repository;

import young.board.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    public Long save(Post post);

    public Optional<Post> findOne(Long postId);

    public List<Post> findAll();

}
