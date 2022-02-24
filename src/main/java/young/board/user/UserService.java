package young.board.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.board.comment.Comment;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long joinUser(String stringId, String password) {
        Optional<User> findUserByStringId = userRepository.findByStringId(stringId);
        if (findUserByStringId.isPresent()) {
            throw new IllegalStateException("이미 존재하는 사용자 ID입니다.");
        } //TODO 근데 StringId 중복 테스트 User엔티티에 들어있긴 한데 해줘야 하나?
        User user = User.builder()
                .stringId(stringId)
                .password(password)
                .build();
        return userRepository.save(user);
    }

    public Object login(String stringId, String password) {
        Optional<User> parsingUser = userRepository.findByStringId(stringId);
        if (parsingUser.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 사용자거나, 비밀번호를 잘못 입력하셨습니다.");
        }
        User user = parsingUser.get();
        if (!user.getPassword().equals(password)) {
            throw new IllegalStateException("존재하지 않는 사용자거나, 비밀번호를 잘못 입력하셨습니다.");
        }
        //TODO 로그인이 됨. 리턴값도 뭘로 할지 고민하기.
        return null;
    }

}
