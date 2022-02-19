package young.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static young.board.message.ErrorMessage.ALREADY_CLICKED_DISLIKE_ERROR;
import static young.board.message.ErrorMessage.ALREADY_CLICKED_LIKE_ERROR;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommendation {
    @Id
    @GeneratedValue
    @Column(name = "recommendation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @Enumerated(EnumType.STRING)
    private RecommendationStatus recommendationStatus;

    public static Recommendation createRecommendation(Post post, Users user) {
        Recommendation like = new Recommendation();
        like.post = post;
        like.user = user;
        like.recommendationStatus = RecommendationStatus.NONE;
        return like;
    }

    @Builder
    private Recommendation(Post post, Users user) {
        Assert.notNull(post, "Post 들어오지 않음");
        Assert.notNull(user, "User 들어오지 않음");

        this.post = post;
        this.user = user;
        this.recommendationStatus = RecommendationStatus.NONE;
    }

    public void dislike() {
        if (this.recommendationStatus == RecommendationStatus.DISLIKE) {
            throw new IllegalArgumentException(ALREADY_CLICKED_DISLIKE_ERROR);
        }
        this.recommendationStatus = RecommendationStatus.DISLIKE;
    }

    public void like() {
        if (this.recommendationStatus == RecommendationStatus.LIKE) {
            throw new IllegalArgumentException(ALREADY_CLICKED_LIKE_ERROR);
        }
        this.recommendationStatus = RecommendationStatus.LIKE;
    }

    public void cancel() {
        this.recommendationStatus = RecommendationStatus.NONE;
    }

}