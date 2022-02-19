package young.board.controller.form;

import lombok.Getter;
import lombok.Setter;
import young.board.domain.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static young.board.constants.PostConstant.*;
import static young.board.message.ErrorMessage.*;

@Getter @Setter
public class PostCreateForm {

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

}
