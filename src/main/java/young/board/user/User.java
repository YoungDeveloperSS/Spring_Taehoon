package young.board.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String stringId; //todo 이름을 어떻게 해야하지? 위에를 id 아니라 시리얼넘버로 바꿔줄까?

    @Column(nullable = false)
    private String password;

    @Builder
    private User(String stringId, String password) {
        //TODO validate
        this.stringId = stringId;
        this.password = password;
    }
}
