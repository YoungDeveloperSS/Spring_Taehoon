package young.board;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
public class Like {
    private Integer likeNumber = 0; //디폴트값을 이렇게 표시하는게 맞나?

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
