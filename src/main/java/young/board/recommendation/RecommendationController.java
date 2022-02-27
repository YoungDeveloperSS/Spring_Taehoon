package young.board.recommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static young.board.constants.PostConstant.TEMP_USER_ID;

@Controller
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId,
                                           @RequestParam(defaultValue = TEMP_USER_ID) Long userId) {
        try {
            Long recommendationId = recommendationService.likePost(postId, userId);
            return ResponseEntity.ok().body("like success\nid = " + recommendationId);
            // TODO 201로 줘야 하는건지 고민, 그리고 header, body 이 부분 http 잘 공부한 뒤 고민해보자.
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/posts/{postId}/dislike")
    public ResponseEntity<String> dislikePost(@PathVariable Long postId,
                                              @RequestParam(defaultValue = TEMP_USER_ID) Long userId) {
        try {
            Long recommendationId = recommendationService.dislikePost(postId, userId);
            return ResponseEntity.ok().body("dislike success\nid = " + recommendationId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/posts/{postId}/like-cancel")
    public ResponseEntity<String> cancelRecommendation(@PathVariable Long postId,
                                                       @RequestParam(defaultValue = TEMP_USER_ID) Long userId) {
        try {
            Long recommendationId = recommendationService.cancelLikeOrDislike(postId, userId);
            return ResponseEntity.ok("cancel success\nid = " + recommendationId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
