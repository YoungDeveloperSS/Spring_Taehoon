package young.board.recommendation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import young.board.post.Post;
import young.board.user.User;

import javax.persistence.*;

import static young.board.message.ErrorMessage.ALREADY_CLICKED_DISLIKE_ERROR;
import static young.board.message.ErrorMessage.ALREADY_CLICKED_LIKE_ERROR;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private RecommendationStatus recommendationStatus;

    @Builder
    private Recommendation(Post post, User user) {
        Assert.notNull(post, "Post 들어오지 않음");
        Assert.notNull(user, "User 들어오지 않음");

        this.post = post;
        this.user = user;
        this.recommendationStatus = RecommendationStatus.NOT_USING;
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
        this.recommendationStatus = RecommendationStatus.NOT_USING;
    }

}