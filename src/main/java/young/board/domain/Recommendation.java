package young.board.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
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

    public void dislike() {
        if (this.recommendationStatus == RecommendationStatus.DISLIKE) {
            throw new IllegalArgumentException("이미 싫어요를 누른 게시물입니다.");
        }
        this.recommendationStatus = RecommendationStatus.DISLIKE;
    }

    public void cancel() {
        this.recommendationStatus = RecommendationStatus.NONE;
    }

    public void like() {
        if (this.recommendationStatus == RecommendationStatus.LIKE) {
            throw new IllegalArgumentException("이미 좋아요를 누른 게시물입니다.");
        }
        this.recommendationStatus = RecommendationStatus.LIKE;
    }
}