package young.board.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import young.board.post.controller.form.PostCreateForm;
import young.board.post.controller.form.PostEditForm;
import young.board.comment.CommentService;
import young.board.post.service.PostResponseServiceDto;
import young.board.post.service.PostService;
import young.board.recommendation.RecommendationService;
import young.board.domain.Category;

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

    // TODO : 분명 전체 페이지 수도 보내줘야 하는데, 어떻게 여러 개의 값을 보내주지? 지금 내가 생각을 잘못 하고 있는거같음.
    //  나는 프론트엔드 애들이 가져다가 쓰는 코드를 만드는 중. 그렇다면 여러 url이 합쳐져서 하나의 페이지를 만드는 거 아닐까?
    //  하나의 url에 하나의 화면에 들어가는 모든 값을 넣을 필요 없을까?
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
//            TODO 질문하기 : 어떻게 RestController에서 redirect할 수 있지? 이 방식으로 하면 DELETE 기능이 존재 안안하고 에러가 뜨는데,
//             아마 ~/posts DELETE가 실행되는 게 아닌가 싶음. 인터넷 찾아도 못찾겠다는 느낌. 질문 한 번 드려보자.

//             return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/posts")).build();
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
            //이런 경우 id값이 필요한거같은데, id 넘겨야 하나?
            return ResponseEntity.ok("update complete");
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
            return ResponseEntity.ok("create complete");
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
