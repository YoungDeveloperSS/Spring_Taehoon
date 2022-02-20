package young.board.post.controller.form;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import young.board.domain.Category;
import young.board.post.service.PostResponseServiceDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static young.board.constants.PostConstant.*;
import static young.board.constants.PostConstant.WRITER_MAX_LENGTH;
import static young.board.message.ErrorMessage.*;
import static young.board.message.ErrorMessage.CATEGORY_NOT_INPUT_ERROR;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostEditForm {

    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @NotBlank(message = TITLE_NOT_INPUT_ERROR)
    @Size(min = TITLE_MIN_LENGTH, max = TITLE_MAX_LENGTH, message = TITLE_LENGTH_ERROR)
    private String title;

    @NotBlank(message = WRITER_NOT_INPUT_ERROR)
    @Size(min = WRITER_MIN_LENGTH, max = WRITER_MAX_LENGTH, message = WRITER_LENGTH_ERROR)
    private String writer;

    @NotNull(message = CONTENT_NOT_INPUT_ERROR)
    private String content;

    @NotNull(message = CATEGORY_NOT_INPUT_ERROR)
    private Category category;

    public static PostEditForm createPostEditForm(PostResponseServiceDto postResponseDto) {
        PostEditForm postEditForm = new PostEditForm();
        postEditForm.setId(postResponseDto.getId());
        postEditForm.setTitle(postResponseDto.getTitle());
        postEditForm.setWriter(postResponseDto.getWriter());
        postEditForm.setContent(postResponseDto.getContent());
        postEditForm.setCategory(postResponseDto.getCategory());
        return postEditForm;
    }

}
