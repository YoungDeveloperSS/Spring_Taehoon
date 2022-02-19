package young.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import young.board.controller.form.PostCreateForm;
import young.board.controller.form.PostEditForm;
import young.board.controller.response.PostResponse;
import young.board.service.PostService;
import young.board.service.RecommendationService;
import young.board.domain.Category;
import young.board.domain.Post;

import java.util.List;
import java.util.stream.Collectors;

import static young.board.constants.PostConstant.*;
import static young.board.message.ErrorMessage.PARAM_FORM_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final RecommendationService recommendationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK) //OK말고 다른 상황은 없으니까
    public List<PostResponse> showPostList() {
        List<Post> posts = postService.findAll();
        List<PostResponse> postResponses = posts.stream().map(post ->
                        PostResponse.create(post, recommendationService.calculateLikesCnt(post.getId())))
                .collect(Collectors.toList());
        return postResponses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> showPostDetail(@PathVariable Long id) {
        try {
            Post post = postService.findPost(id);
            PostResponse postResponse = PostResponse.create(post, recommendationService.calculateLikesCnt(post.getId()));
            return new ResponseEntity<>(postResponse, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>("delete complete", HttpStatus.OK);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<PostEditForm> editPostForm(@PathVariable Long id) {
        try {
            Post post = postService.findPost(id);
            PostEditForm form = PostEditForm.createPostEditForm(post);
            return new ResponseEntity<>(form, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> editPost(@PathVariable Long id, @ModelAttribute PostEditForm form) {
        //TODO 검증 -> View에서도 검증 해줘야함.
        try {
            validatePostForm(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            postService.update(id, form.getTitle(), form.getWriter(),
                    form.getContent(), form.getCategory());
            //이런 경우 id값이 필요한거같은데, id 넘겨야 하나?
            return new ResponseEntity<>("update complete", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
//        return "redirect:/posts/" + id;
    }

    @GetMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public PostCreateForm createPostForm() {
        return new PostCreateForm();
    }

    @PostMapping
    public ResponseEntity<String> createPost(@ModelAttribute PostCreateForm form) {
        //TODO 검증 -> View에서도 검증 해줘야함.
        try {
            validatePostForm(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            Long savedId = postService.save(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            return new ResponseEntity<>("create complete", HttpStatus.OK);
            //이런 경우 id값이 필요한거같은데, id 넘겨야 하나?
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void validatePostForm(String title, String writer, String content, Category category) {
        if (!StringUtils.hasText(title) || !StringUtils.hasText(writer) ||
                content == null || category == null ||
                title.length() < TITLE_MIN_LENGTH || title.length() > TITLE_MAX_LENGTH ||
                writer.length() < WRITER_MIN_LENGTH || writer.length() > WRITER_MAX_LENGTH) {
            throw new IllegalArgumentException(PARAM_FORM_ERROR);
        }
    }

}
