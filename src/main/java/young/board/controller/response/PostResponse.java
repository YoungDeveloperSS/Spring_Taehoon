package young.board.controller.response;

import lombok.Getter;
import young.board.domain.Category;
import young.board.domain.Post;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
public class PostResponse {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;
    private String writer;
    private LocalDateTime createDate;
    private String content;

    private Integer likeNumber;

    public static PostResponse create(Post post, Integer calculateLikesCnt) {
        PostResponse postResponse = new PostResponse();
        postResponse.id = post.getId();
        postResponse.title = post.getTitle();
        postResponse.category = post.getCategory();
        postResponse.content = post.getContent();
        postResponse.writer = post.getWriter();
        postResponse.content = post.getContent();
        postResponse.createDate = post.getCreateDate();
        postResponse.likeNumber = calculateLikesCnt;
        return postResponse;
    }
}
