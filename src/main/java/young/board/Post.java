package young.board;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;
    private String writer;
    private LocalDateTime createDate;
    private String content;

    @Embedded
    private Like likeNumber;

    protected Post() {
    }

    public static Post createPost(String title, Category category, String writer, String content) {
        Post post = new Post();
        post.title = title;
        post.category = category;
        post.writer = writer;
        post.content = content;
        post.createDate = LocalDateTime.now();
        post.likeNumber = new Like();
        return post;
    }
}
