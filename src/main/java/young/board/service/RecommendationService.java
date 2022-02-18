package young.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.board.domain.RecommendationStatus;
import young.board.domain.Recommendation;
import young.board.domain.Post;
import young.board.domain.Users;
import young.board.repository.recommendation.RecommendationRepository;
import young.board.repository.post.PostRepository;
import young.board.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static young.board.message.ErrorMessage.NOT_EXIST_POST_ERROR;
import static young.board.message.ErrorMessage.NOT_EXIST_USER_ERROR;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendationService {
    private final RecommendationRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Integer calculateLikesCnt(Long postId) {
        List<Recommendation> recommendations = likeRepository.findRecommendationByPostId(postId);
        Integer likeCnt = 0;
        for (Recommendation recommendation : recommendations) {
            if (recommendation.getRecommendationStatus() == RecommendationStatus.LIKE) {
                likeCnt += 1;
            }
            else if (recommendation.getRecommendationStatus() == RecommendationStatus.DISLIKE) {
                likeCnt -= 1;
            }
        }
        return likeCnt;
    }

    @Transactional
    public Long likePost(Long postId, Long userId) {
        Recommendation recommendation = getRecommendation(postId, userId);
        recommendation.like();
        return recommendation.getId();
    }

    @Transactional
    public Long dislikePost(Long postId, Long userId) {
        Recommendation recommendation = getRecommendation(postId, userId);
        recommendation.dislike();
        return recommendation.getId();
    }

    @Transactional
    public Long cancelLikeOrDislike(Long postId, Long userId) {
        Recommendation recommendation = getRecommendation(postId, userId);
        recommendation.cancel();
        return recommendation.getId();
    }

    private Users validateUserIsExisted(Long userId) {
        Optional<Users> wrappingUser = userRepository.findOne(userId);
        if (wrappingUser.isEmpty()) {
            throw new IllegalArgumentException(NOT_EXIST_USER_ERROR);
        }
        return wrappingUser.get();
    }

    private Post validatePostIsExisted(Long postId) {
        Optional<Post> wrappingPost = postRepository.findOne(postId);
        if (wrappingPost.isEmpty()) {
            throw new IllegalArgumentException(NOT_EXIST_POST_ERROR);
        }
        return wrappingPost.get();
    }

    private Recommendation getRecommendation(Long postId, Long userId) {
        Post post = validatePostIsExisted(postId);
        Users user = validateUserIsExisted(userId);
        Optional<Recommendation> wrappingLike = likeRepository.findRecommendationByPostIdAndUserId(postId, userId);
        if (wrappingLike.isEmpty()) {
            Recommendation recommendation = Recommendation.createRecommendation(post, user);
            likeRepository.save(recommendation);
            return recommendation;
        }
        return wrappingLike.get();
    }
}
