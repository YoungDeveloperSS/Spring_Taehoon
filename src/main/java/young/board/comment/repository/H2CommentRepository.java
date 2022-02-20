package young.board.comment.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import young.board.domain.Comment;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class H2CommentRepository implements CommentRepository {

    private final EntityManager em;

    @Override
    public Long save(Comment comment) {
        em.persist(comment);
        return comment.getId();
    }

    @Override
    public List<Comment> findAllUsingPostId(Long postId) {
        return em.createQuery("select c from Comment c where c.post.id = :postId", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public Optional<Comment> findOne(Long commentId) {
        return Optional.ofNullable(em.find(Comment.class, commentId));
    }
}
