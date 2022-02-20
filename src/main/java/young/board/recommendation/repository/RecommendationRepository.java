package young.board.recommendation.repository;

import young.board.domain.Recommendation;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository {

    public Long save(Recommendation recommendation);

    public Optional<Recommendation> findOne(Long recommendationId);

    public List<Recommendation> findAll();

    public List<Recommendation> findRecommendationByPostId(Long recommendationId);

    Optional<Recommendation> findRecommendationByPostIdAndUserId(Long postId, Long userId);

}
