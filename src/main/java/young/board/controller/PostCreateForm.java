package young.board.controller;

import lombok.Getter;
import lombok.Setter;
import young.board.domain.Category;

@Getter @Setter
public class PostCreateForm {
    private String title;
    private String writer;
    private String content;
    private Category category;

}
