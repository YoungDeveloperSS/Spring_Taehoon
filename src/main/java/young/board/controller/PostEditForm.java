package young.board.controller;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import young.board.domain.Category;
import young.board.domain.Post;

@Getter @Setter
public class PostEditForm {
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private String title;
    private String writer;
    private String content;
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
