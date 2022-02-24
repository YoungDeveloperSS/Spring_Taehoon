package young.board.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import young.board.comment.service.CommentService;


@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PatchMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<String> editComment(@PathVariable Long commentId, CommentRequestDto commentRequestDto) {
        try {
            //TODO form 검증
            Long updateCommentId = commentService.update(commentId, commentRequestDto.getContent(), commentRequestDto.getWriter());
            return ResponseEntity.ok("comment edit complete\ncommentId : " + updateCommentId);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        } //IllegalArgu... -> 400에러
    }

    @DeleteMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        try {
            //TODO form 검증
            commentService.delete(commentId);
            return ResponseEntity.ok("comment delete complete");
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        } //IllegalArgu... -> 400에러
    }

    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<String> comment(@PathVariable Long postId, CommentRequestDto commentRequestDto) { //create, updateForm 나눠야 함
        try {
            //TODO form 검증
            commentService.comment(postId, commentRequestDto.getContent(), commentRequestDto.getWriter());
            return ResponseEntity.ok("comment complete");
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        } //IllegalArgu... -> 400에러

    }
}
