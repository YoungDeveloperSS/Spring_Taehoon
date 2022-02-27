package young.board.recommendation.repository;

import young.board.recommendation.Recommendation;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository {

    public Long save(Recommendation recommendation);

    public Optional<Recommendation> findOne(Long recommendationId);

    public List<Recommendation> findAll();

    public List<Recommendation> findRecommendationsByPostId(Long recommendationId);

    Optional<Recommendation> findOneByPostIdAndUserId(Long postId, Long userId);

}
