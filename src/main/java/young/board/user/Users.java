package young.board.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PUBLIC) // TODO protected여야함. 테스트때문에 PUBLIC 열어둠. 관련된 메서드 하나도안만들어서
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

}
