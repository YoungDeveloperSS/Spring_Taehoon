package young.board.post.controller.response;

import lombok.Builder;
import lombok.Getter;
import young.board.image.Image;
import young.board.post.Category;
import young.board.comment.Comment;
import young.board.post.service.PostResponseServiceDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {

    private Long id;
    private Category category;
    private String title;
    private String writer;
    private LocalDateTime createDate;
    private String content;
    //TODO comments, images 양방향 빼주고 여기서 넣어주자. 아니면 양방향 넣는게 이득인가? LAZY로 설정하면?
    private List<Comment> comments;
    private List<Image> images;
    private Integer likeNumberCnt;


    @Builder
    private PostResponseDto(PostResponseServiceDto postResponseServiceDto, List<Image> images, Integer likeNumberCnt) {
        this.id = postResponseServiceDto.getId();
        this.title = postResponseServiceDto.getTitle();
        this.category = postResponseServiceDto.getCategory();
        this.content = postResponseServiceDto.getContent();
        this.writer = postResponseServiceDto.getWriter();
        this.createDate = postResponseServiceDto.getCreateDate();
        this.content = postResponseServiceDto.getContent();
        this.comments = postResponseServiceDto.getComments();
        this.images = images;
        this.likeNumberCnt = likeNumberCnt;

    }

}
