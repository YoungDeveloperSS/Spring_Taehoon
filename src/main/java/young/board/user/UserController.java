package young.board.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import young.board.config.security.JwtTokenProvider;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody Map<String, String> user) {
        try {
            String stringId = user.get("stringId");
            String password = user.get("password");
            return ResponseEntity.ok(userService.joinUser(stringId, password));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> user) {
        try {
            String stringId = user.get("stringId");
            String password = user.get("password");
            return ResponseEntity.ok(userService.login(stringId, password));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
