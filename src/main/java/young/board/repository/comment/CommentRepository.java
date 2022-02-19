package young.board.repository.comment;

import young.board.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Long save(Comment comment);

    List<Comment> findAllUsingPostId(Long postId);

    Optional<Comment> findOne(Long commentId);

}
