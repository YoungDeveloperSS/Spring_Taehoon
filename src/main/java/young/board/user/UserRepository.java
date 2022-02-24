package young.board.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findById(Long id);

    public Optional<User> findByStringId(String stringId);

    public Long save(User user);
//
//    public Optional<User> findOne(Long id);
}
