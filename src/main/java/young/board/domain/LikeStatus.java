package young.board.domain;

import lombok.Getter;

@Getter
public enum LikeStatus {
    LIKE("좋아요"), DISLIKE("싫어요"), NONE("상태없음"); //TODO NONE 내부를 null로 하는게 맞을까? 다 만든 뒤 고민하기.

    private String likeStatus;

    LikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }
}
