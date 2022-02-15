package young.board.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import young.board.domain.Category;
import young.board.domain.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static young.board.constants.PostConstant.*;
import static young.board.constants.PostConstant.WRITER_MAX_LENGTH;

@Getter @Setter
public class PostEditForm {
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = TITLE_MIN_LENGTH, max = TITLE_MAX_LENGTH, message = "제목은 1글자 이상 20글자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "작성자를 입력해주세요.")
    @Size(min = WRITER_MIN_LENGTH, max = WRITER_MAX_LENGTH, message = "작성자의 이름은 1글자 이상 8글자 이하여야 합니다.")
    private String writer;

    @NotNull(message = "내용을 입력해주세요.")
    private String content;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category;

    public static PostEditForm createPostEditForm(Post post) {
        PostEditForm postEditForm = new PostEditForm();
        postEditForm.setId(post.getId());
        postEditForm.setTitle(post.getTitle());
        postEditForm.setWriter(post.getWriter());
        postEditForm.setContent(post.getContent());
        postEditForm.setCategory(post.getCategory());
        return postEditForm;
    }

}
