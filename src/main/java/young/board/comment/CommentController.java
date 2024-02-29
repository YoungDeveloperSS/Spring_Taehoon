package young.board.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    //TODO 코멘트 컨트롤러는 사실상 PostController에 종속적인데, 어떤 식으로 구조를 짜야하지?
    private final CommentService commentService;

    @PatchMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<String> editComment(@PathVariable Long commentId, CommentForm commentForm) { //postId 필요없는데 꼭 써야하나?
        try {
            //TODO form 검증
            Long updateCommentId = commentService.update(commentId, commentForm.getContent(), commentForm.getWriter());
            return ResponseEntity.ok("comment edit complete\ncommentId : " + updateCommentId);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        } //IllegalArgu... -> 400에러
    }

    @DeleteMapping("/posts/{postId}/comment/{commentId}") //다시 원래 Post로 redirect하는건, 음.. Comment가 들고있는 PostId로 하면될듯?
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) { //postId 필요없는데 꼭 써야하나?
        try {
            //TODO form 검증
            commentService.delete(commentId);
            return ResponseEntity.ok("comment delete complete");
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        } //IllegalArgu... -> 400에러
    }

    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<String> comment(@PathVariable Long postId, CommentForm commentForm) { //create, updateForm 나눠야 함
        try {
            //TODO form 검증
            commentService.comment(postId, commentForm.getContent(), commentForm.getWriter());
            return ResponseEntity.ok("comment complete");
            // TODO created url 의미?
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        } //IllegalArgu... -> 400에러

    }
}
