package young.board.controller;

import lombok.Getter;
import lombok.Setter;
import young.board.domain.Category;
import young.board.domain.Post;

@Getter @Setter
public class PostEditForm {
    private String title;
    private String writer;
    private String content;
    private Category category;

    public PostEditForm(Post post) {
        this.title = post.getTitle();
        this.writer = post.getWriter();
        this.content = post.getContent();
        this.category = post.getCategory();
    }
}
