package young.board.post.controller.response;

import lombok.Builder;
import lombok.Getter;
import young.board.comment.Comment;
import young.board.image.Image;
import young.board.post.Category;
import young.board.post.service.PostResponseServiceDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ShowPostListResponseDto {
    //TODO 클래스이름 관련 고민. ShowPostResponseDto가 맞는거같음. (controller에서 List<이 타입> 으로 보내니깐)
    private Long id;
    private Category category;
    private String title;
    private String writer;
    private LocalDateTime createDate;
    private String content;
    private Integer likeNumberCnt; //PostResponseServiceDto + 추가된 필드.

    @Builder
    private ShowPostListResponseDto(PostResponseServiceDto postResponseServiceDto, Integer likeNumberCnt) {
        this.id = postResponseServiceDto.getId();
        this.title = postResponseServiceDto.getTitle();
        this.category = postResponseServiceDto.getCategory();
        this.content = postResponseServiceDto.getContent();
        this.writer = postResponseServiceDto.getWriter();
        this.content = postResponseServiceDto.getContent();
        this.createDate = postResponseServiceDto.getCreateDate();
        this.likeNumberCnt = likeNumberCnt;
    }

}
