package young.board.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import young.board.image.Image;
import young.board.image.ImageService;
import young.board.post.controller.request.PostCreateRequestDto;
import young.board.post.controller.request.PostEditRequestDto;
import young.board.post.controller.response.PostDetailResponseDto;
import young.board.post.controller.response.PostSummaryResponseDto;
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
    //TODO 내일 더 할것.
    // createForm, editForm DTO로 변경
    // images 역시 같이 왔다갔다.
    // 실제 s3 사용해서 db에 사진 몇개 넣어보고, url로 왔다갔다 테스트해보기.

    private final PostService postService;
    private final RecommendationService recommendationService;
    private final ImageService imageService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostSummaryResponseDto> loadPostSummaryList(@RequestParam(defaultValue = "1") int page) {
        List<PostResponseServiceDto> postResponseServiceDtos = postService.findAll(page);
        return postResponseServiceDtos.stream().map(postResponseServiceDto ->
                        PostSummaryResponseDto.builder()
                                .postResponseServiceDto(postResponseServiceDto)
                                .likeNumberCnt(getLikeNumberCnt(postResponseServiceDto))
                                .build())
                .collect(Collectors.toList());
    }

    private Integer getLikeNumberCnt(PostResponseServiceDto postResponseServiceDto) {
        return recommendationService.calculateLikesCnt(postResponseServiceDto.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetailResponseDto> loadPostDetail(@PathVariable Long id) {
        try {
            PostResponseServiceDto postResponseServiceDto = postService.findPost(id);
            List<Image> images = imageService.inqueryImagesUsingPostId(id);
            PostDetailResponseDto postDetailResponseDto = PostDetailResponseDto.builder()
                    .postResponseServiceDto(postResponseServiceDto)
                    .likeNumberCnt(getLikeNumberCnt(postResponseServiceDto))
                    .images(images)
                    .build();
            return ResponseEntity.ok(postDetailResponseDto);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            imageService.deleteImagesThisPost(id);
            return ResponseEntity.ok("delete complete");
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> editPost(@PathVariable Long id, @ModelAttribute PostEditRequestDto form) {
        //TODO 검증 -> View에서도 검증 해줘야함.
        try {
            validatePostForm(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            postService.update(id, form.getTitle(), form.getWriter(),
                    form.getContent(), form.getCategory());
            imageService.updateImageInfos(id, TEMP_USER_ID, form.getImageInfos());
            return ResponseEntity.ok("update complete");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createPost(@ModelAttribute PostCreateRequestDto form) {
        //TODO 검증 -> View에서도 검증 해줘야함.
        try {
            validatePostForm(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            Long savedId = postService.save(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            imageService.uploadImagesThisPost(savedId, TEMP_USER_ID, form.getImageInfos());
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
