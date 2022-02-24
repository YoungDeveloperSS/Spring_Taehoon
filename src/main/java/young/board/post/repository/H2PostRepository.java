package young.board.post.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import young.board.post.Post;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static young.board.constants.PostConstant.POST_CNT_PER_PAGE;

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
    public List<Post> findAll(int page) {
        return em.createQuery("select p from Post p where p.isNotUsing = false",Post.class)
                .setFirstResult((page-1)*POST_CNT_PER_PAGE) //1페이지 -> 0번째부터 9까지
                .setMaxResults(POST_CNT_PER_PAGE)
                .getResultList();
    }

}
