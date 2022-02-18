package young.board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Users {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

}
