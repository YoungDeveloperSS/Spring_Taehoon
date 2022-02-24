package young.board.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class H2UserRepository implements UserRepository {
    private final EntityManager em;

    @Override
    public Long save(Users user) {
        em.persist(user);
        return user.getId();
    }

    @Override
    public Optional<Users> findOne(Long id) {
        return Optional.ofNullable(em.find(Users.class, id));
    }
}
