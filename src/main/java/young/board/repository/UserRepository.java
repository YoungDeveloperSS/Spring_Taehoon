package young.board.repository;

import young.board.domain.Users;

import java.util.Optional;

public interface UserRepository {
    public Long save(Users user);

    public Optional<Users> findOne(Long id);
}
