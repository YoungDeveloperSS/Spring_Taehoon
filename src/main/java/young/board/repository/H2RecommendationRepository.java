package young.board.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import young.board.domain.Recommendation;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class H2RecommendationRepository implements RecommendationRepository {
    private final EntityManager em;

    @Override
    public Long save(Recommendation like) {
        em.persist(like);
        return like.getId();
    }

    @Override
    public Optional<Recommendation> findOne(Long likeId) {
        return Optional.ofNullable(em.find(Recommendation.class, likeId));
    }

    @Override
    public List<Recommendation> findAll() {
        return em.createQuery("select r from Recommendation r", Recommendation.class)
                .getResultList();
    }

    @Override
    public List<Recommendation> findRecommendationByPostId(Long postId) {
        return em.createQuery("select r from Recommendation r where r.post.id = :postId", Recommendation.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public Optional<Recommendation> findRecommendationByPostIdAndUserId(Long postId, Long userId) {
        return em.createQuery("select r from Recommendation r " +
                        "where r.post.id = :postId and r.user.id = :userId", Recommendation.class)
                .setParameter("postId", postId)
                .setParameter("userId", userId)
                .getResultList()
                .stream()
                .findFirst();
    }

}
