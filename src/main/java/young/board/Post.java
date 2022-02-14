package young.board;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;
    private String writer;
    private LocalDateTime createDate;
    private String content;

    @Embedded
    private Like likeNumber;
}
