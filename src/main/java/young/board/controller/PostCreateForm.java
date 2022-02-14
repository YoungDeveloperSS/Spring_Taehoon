package young.board.controller;

import lombok.Getter;
import lombok.Setter;
import young.board.domain.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class PostCreateForm {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 20, message = "제목은 1글자 이상 20글자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "작성자를 입력해주세요.")
    @Size(min = 1, max = 8, message = "작성자의 이름은 1글자 이상 8글자 이하여야 합니다.")
    private String writer;

    @NotNull(message = "내용을 입력해주세요.")
    private String content;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category;

}
