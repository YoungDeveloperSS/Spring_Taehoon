package young.board.recommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId, Model model, @RequestParam Long userId) {
        try {
            recommendationService.likePost(postId, userId);
            return new ResponseEntity<>("like success", HttpStatus.OK); //300번대로
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/posts/{postId}/dislike")
    public ResponseEntity<String> dislikePost(@PathVariable Long postId, Model model, @RequestParam Long userId) {
        try {
            recommendationService.dislikePost(postId, userId);
            return new ResponseEntity<>("dislike success", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/posts/{postId}/like-cancel")
    public ResponseEntity<String> cancelRecommendation(@PathVariable Long postId, Model model, @RequestParam Long userId) {
        try {
            recommendationService.cancelLikeOrDislike(postId, userId);
            return new ResponseEntity<>("cancel success", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
//        return "redirect:/posts/" + postId;
    }
}
