package young.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import young.board.domain.Post;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class H2PostRepository implements PostRepository {
    private final EntityManager em;

    @Override
    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    @Override
    public Optional<Post> findOne(Long postId) {
        return Optional.ofNullable(em.find(Post.class, postId));
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("select p from Post p",Post.class)
                .getResultList();
    }

//    public Long update(Long postId, String title, String writer, String content, Category category) { ->서비스로 이동

    @Override
    public void delete(Post post) {
        em.remove(post);
    }
}
