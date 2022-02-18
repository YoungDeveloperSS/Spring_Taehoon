package young.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;
    private String writer;
    private LocalDateTime createDate;
    private String content;

    public static Post createPost(String title, Category category, String writer, String content) {
        Post post = new Post();
        post.title = title;
        post.category = category;
        post.writer = writer;
        post.content = content;
        post.createDate = LocalDateTime.now();
        return post;
    }

    public void update(String title, String writer, String content, Category category) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.category = category;
    }
}
