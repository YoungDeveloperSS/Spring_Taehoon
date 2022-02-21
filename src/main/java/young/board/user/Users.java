package young.board.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PUBLIC) //protected여야함. 테스트때문에
public class Users {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

}
