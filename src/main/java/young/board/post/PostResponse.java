package young.board.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import young.board.domain.Category;
import young.board.domain.Comment;
import young.board.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponse {

    private Long id;
    private Category category;
    private String title;
    private String writer;
    private LocalDateTime createDate;
    private String content;
    private List<Comment> comments;

    private Integer likeNumber;

    public static PostResponse create(Post post, Integer calculateLikesCnt, List<Comment> comments) {
        PostResponse postResponse = new PostResponse();
        postResponse.id = post.getId();
        postResponse.title = post.getTitle();
        postResponse.category = post.getCategory();
        postResponse.content = post.getContent();
        postResponse.writer = post.getWriter();
        postResponse.content = post.getContent();
        postResponse.createDate = post.getCreateDate();
        postResponse.likeNumber = calculateLikesCnt;
        postResponse.comments = comments;

        return postResponse;
    }
}
