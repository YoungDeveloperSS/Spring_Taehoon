package young.board.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

import static young.board.constants.PostConstant.DEFAULT_LIKE_NUM;

@Embeddable
@Getter
public class Like {
    private Integer likeNumber = DEFAULT_LIKE_NUM; //디폴트값을 이렇게 표시하는게 맞나?

    public void like() {
        likeNumber += 1;
    }

    public void disLike() {
        likeNumber -= 1;
    }

    @Override
    public String toString() {
        return Integer.toString(likeNumber);
    }
}
