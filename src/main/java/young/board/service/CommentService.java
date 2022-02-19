package young.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.board.domain.Comment;
import young.board.domain.Post;
import young.board.repository.comment.CommentRepository;
import young.board.repository.post.PostRepository;

import java.util.List;
import java.util.Optional;

import static young.board.message.ErrorMessage.NOT_EXIST_COMMENT_ERROR;
import static young.board.message.ErrorMessage.NOT_EXIST_POST_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long comment(Long postId, String commentContent, String writer) {
        Post post = validatePostExist(postId);
        Comment comment = Comment.builder()
                .post(post)
                .comment(commentContent) // TODO Comment도메인에 comment속성은 안만드는게 낫겠다. 이후에 리팩토링 해주자.
                .writer(writer)
                .build();
        return commentRepository.save(comment);
    }

    public List<Comment> inqueryCommentsOnPost(Long postId) {
        return commentRepository.findAllUsingPostId(postId);
    }

    @Transactional
    public Long update(Long commentId, String commentContent, String writer) {
        // 일일히 파라미터로 받기 싫으면 서비스계층 DTO를 만들어라.
        Comment comment = validateCommentExist(commentId);
        comment.update(commentContent, writer);
        return comment.getId();
    }

    @Transactional
    public void delete(Long commentId) {
        Comment comment = validateCommentExist(commentId);
        comment.delete();
    }

    private Comment validateCommentExist(Long commentId) {
        Optional<Comment> parsingComment = commentRepository.findOne(commentId);
        if (parsingComment.isEmpty()) {
            throw new IllegalStateException(NOT_EXIST_COMMENT_ERROR);
        }
        Comment comment = parsingComment.get();
        if (comment.isNotUsing()) {
            throw new IllegalStateException(NOT_EXIST_COMMENT_ERROR);
        }
        return comment;
    }

    private Post validatePostExist(Long postId) {
        Optional<Post> parsingPost = postRepository.findOne(postId);
        if (parsingPost.isEmpty()) {
            throw new IllegalStateException(NOT_EXIST_POST_ERROR);
        }
        Post post = parsingPost.get();
        if (post.isNotUsing()) { //todo 왜 메서드가 자동으로 만들어지는지 알아보기.
            throw new IllegalStateException(NOT_EXIST_POST_ERROR);
        }
        return post;
    }

}
