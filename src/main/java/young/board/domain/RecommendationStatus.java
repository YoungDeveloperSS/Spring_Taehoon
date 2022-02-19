package young.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access=AccessLevel.PRIVATE)
public enum RecommendationStatus {
    LIKE("좋아요"), DISLIKE("싫어요"), NOT_USING("비활성화"); //TODO NONE 내부를 null로 하는게 맞을까? 다 만든 뒤 고민하기.

    private String likeStatus;

    RecommendationStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }
}
