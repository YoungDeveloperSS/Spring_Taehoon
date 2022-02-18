package young.board.domain;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Builder
public class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @NotBlank @Column(nullable = false)
    private String title;

    @NotBlank @Column(nullable = false)
    private String writer;

    @NotNull @Column(nullable = false)
    private LocalDateTime createDate;

    @NotEmpty @Column(nullable = false)
    private String content;

    @Builder(builderClassName = "createPost", builderMethodName = "createPost")
    private Post(Category category, String title, String writer, String content) {
//        validatePostValues(category, title, writer, content);
        Assert.notNull(category, "Post 생성 중 카테고리가 빠졌습니다.");
        Assert.hasText(title, "Post 생성 중 제목이 빠졌습니다.");
        Assert.hasText(writer, "Post 생성 중 작성자가 빠졌습니다.");
        Assert.hasLength(content, "Post 생성 중 내용이 빠졌습니다.");
        // hasText -> " " False hasLength -> " " True

        this.category = category;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.createDate = LocalDateTime.now();
    }

    private void validatePostValues(Category category, String title, String writer, String content) {
        if (category.equals(null)) {
            throw new IllegalArgumentException("category없어");
        }
        if (title.isBlank()) {
            throw new IllegalArgumentException("제목 없어");
        }
        if (writer.isBlank()) {
            throw new IllegalArgumentException("writer 없어");
        }
        if (content.isEmpty()) {
            throw new IllegalArgumentException("content null인가봐");
        }
    }

    public void update(String title, String writer, String content, Category category) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.category = category;
    }
}
