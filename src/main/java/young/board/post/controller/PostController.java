package young.board.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import young.board.post.controller.form.PostCreateForm;
import young.board.post.controller.form.PostEditForm;
import young.board.post.service.PostResponseServiceDto;
import young.board.post.service.PostService;
import young.board.recommendation.RecommendationService;
import young.board.post.Category;

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
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponseDto> showPostList(@RequestParam(defaultValue = "1") int page) {
        List<PostResponseServiceDto> postResponseServiceDtos = postService.findAll(page);
        return postResponseServiceDtos.stream().map(postResponseServiceDto ->
                        PostResponseDto.from(postResponseServiceDto,
                                recommendationService.calculateLikesCnt(postResponseServiceDto.getId())))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> showPostDetail(@PathVariable Long id) {
        try {
            PostResponseServiceDto postResponseServiceDto = postService.findPost(id);
            PostResponseDto postResponseDto = PostResponseDto.from(postResponseServiceDto,
                    recommendationService.calculateLikesCnt(postResponseServiceDto.getId()));
            return ResponseEntity.ok(postResponseDto);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.ok("delete complete");
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<PostEditForm> editPostForm(@PathVariable Long id) {
        try {
            PostResponseServiceDto postResponseDto = postService.findPost(id);
            PostEditForm form = PostEditForm.createPostEditForm(postResponseDto);
            return ResponseEntity.ok(form);
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
            return ResponseEntity.ok("update complete");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
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
            return ResponseEntity.ok("create complete");
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
