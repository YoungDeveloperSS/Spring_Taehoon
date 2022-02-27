package young.board.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.board.config.security.JwtTokenProvider;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public Long joinUser(String stringId, String password) {
        Optional<User> findUserByStringId = userRepository.findByStringId(stringId); //TODO 이렇게 하면 내가 메서드를 정의해줘야 하는건지 확인해보기.
        if (findUserByStringId.isPresent()) {
            throw new IllegalStateException("이미 존재하는 사용자 ID입니다.");
        } //TODO 근데 StringId 중복 테스트 User엔티티에 들어있긴 한데 해줘야 하나?

        return userRepository.save(User.builder()
                .stringId(stringId)
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

    public String login(String stringId, String password) {
        Optional<User> parsingUser = userRepository.findByStringId(stringId);
        if (parsingUser.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 사용자거나, 비밀번호를 잘못 입력하셨습니다.");
        }
        User user = parsingUser.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException("존재하지 않는 사용자거나, 비밀번호를 잘못 입력하셨습니다.");
        }

        return jwtTokenProvider.createToken(stringId, user.getRoles());

    }

}
