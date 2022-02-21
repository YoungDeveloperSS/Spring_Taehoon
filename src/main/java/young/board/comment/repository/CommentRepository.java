package young.board.comment.repository;

import young.board.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Long save(Comment comment);

    List<Comment> findAllUsingPostId(Long postId);

    Optional<Comment> findOne(Long commentId);

}
