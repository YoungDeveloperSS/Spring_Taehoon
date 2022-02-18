package young.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import young.board.RecommendationService;

@Controller
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @PostMapping("/posts/{postId}/like")
    public String likePost(@PathVariable Long postId, Model model, @RequestParam Long userId) {
        try {
            recommendationService.likePost(postId, userId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/dislike")
    public String dislikePost(@PathVariable Long postId, Model model, @RequestParam Long userId) {
        try {
            recommendationService.dislikePost(postId, userId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/like-cancel")
    public String cancelRecommendation(@PathVariable Long postId, Model model, @RequestParam Long userId) {
        try {
            recommendationService.cancelLikeOrDislike(postId, userId);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error-page";
        }
        return "redirect:/posts/" + postId;
    }
}
