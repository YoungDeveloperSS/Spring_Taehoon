package young.board.post.controller;

import young.board.domain.Category;
import young.board.domain.Comment;
import young.board.post.service.PostResponseServiceDto;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDto {

    private Long id;
    private Category category;
    private String title;
    private String writer;
    private LocalDateTime createDate;
    private String content;
    private List<Comment> comments;

    private Integer likeNumberCnt; //PostResponseServiceDto + 추가된 필드.

    public static PostResponseDto from(PostResponseServiceDto postResponseServiceDto, Integer likeNumberCnt) {
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.id = postResponseServiceDto.getId();
        postResponseDto.title = postResponseServiceDto.getTitle();
        postResponseDto.category = postResponseServiceDto.getCategory();
        postResponseDto.content = postResponseServiceDto.getContent();
        postResponseDto.writer = postResponseServiceDto.getWriter();
        postResponseDto.content = postResponseServiceDto.getContent();
        postResponseDto.comments = postResponseServiceDto.getComments();
        postResponseDto.createDate = postResponseServiceDto.getCreateDate();
        postResponseDto.likeNumberCnt = likeNumberCnt;

        return postResponseDto;
    }

}
