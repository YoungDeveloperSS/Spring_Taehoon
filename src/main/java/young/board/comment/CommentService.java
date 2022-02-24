package young.board.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.board.post.Post;
import young.board.comment.repository.CommentRepository;
import young.board.post.repository.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static young.board.message.ErrorMessage.NOT_EXIST_COMMENT_ERROR;
import static young.board.message.ErrorMessage.NOT_EXIST_POST_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long comment(Long postId, String content, String writer) {
        Post post = validatePostExist(postId);
        Comment comment = Comment.builder()
                .post(post)
                .content(content)
                .writer(writer)
                .build();
        return commentRepository.save(comment);
    }

    public List<CommentResponseDto> inqueryCommentsOnPost(Long postId) { // 쓸일 있나? 양방향 매핑을 해줘서 쓸일 없을듯싶은데.
        List<Comment> comments = commentRepository.findAllUsingPostId(postId);
        return comments.stream().map(comment -> CommentResponseDto.from(comment)).collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long commentId, String content, String writer) {
        Comment comment = validateCommentExist(commentId);
        comment.update(content, writer);
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
        if (post.isNotUsing()) {
            throw new IllegalStateException(NOT_EXIST_POST_ERROR);
        }
        return post;
    }

}
