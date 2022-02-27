package young.board.post.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import young.board.image.ImageResponseDto;
import young.board.image.ImageUpdateRequestDto;
import young.board.post.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import static young.board.constants.PostConstant.*;
import static young.board.constants.PostConstant.WRITER_MAX_LENGTH;
import static young.board.message.ErrorMessage.*;
import static young.board.message.ErrorMessage.CATEGORY_NOT_INPUT_ERROR;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostEditRequestDto {

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

    private List<ImageUpdateRequestDto> imageInfos = new ArrayList<>();

}
