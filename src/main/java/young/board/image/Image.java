package young.board.image;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import young.board.post.Post;
import young.board.user.Users;

import javax.persistence.*;
import java.net.URI;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @Column(nullable = false)
    private String uri;

    @Column(name = "image_order")
    private Integer order;

    @Builder
    private Image(Post post, Users user, String uri, Integer order) {
        Assert.notNull(post, "Post 들어오지 않음");
        Assert.notNull(user, "User 들어오지 않음");
        Assert.notNull(uri, "이미지 URI 들어오지 않음");
        Assert.notNull(order, "순서가 들어오지 않음");

        this.post = post;
        this.user = user;
        this.uri = uri;
        this.order = order;
    }

    public void changeOrder(Integer order) {
        //기존에 올린 사진의 순서를 바꿀 순 있어도, 다른 값들은 바꿀 수 없음.
        this.order = order;
    }

}
