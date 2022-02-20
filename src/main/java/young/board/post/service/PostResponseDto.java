package young.board.post.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import young.board.domain.Category;
import young.board.domain.Post;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponseDto {

    private Long id;
    private Category category;
    private String title;
    private String writer;
    private LocalDateTime createDate;
    private String content;
//    private List<Comment> comments; //음... 고민 해보자 다른 방법은 없을까?

//    private Integer likeNumber;

    public static PostResponseDto from(Post post) {
        PostResponseDto postResponse = new PostResponseDto();
        postResponse.id = post.getId();
        postResponse.title = post.getTitle();
        postResponse.category = post.getCategory();
        postResponse.content = post.getContent();
        postResponse.writer = post.getWriter();
        postResponse.content = post.getContent();
        postResponse.createDate = post.getCreateDate();
//        postResponse.likeNumber = calculateLikesCnt;
//        postResponse.comments = comments;
        return postResponse;
    }

}
