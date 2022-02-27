package young.board.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import young.board.image.Image;
import young.board.image.ImageResponseDto;
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
    // TODO 실제 s3 사용해서 db에 사진 몇개 넣어보고, url로 왔다갔다 테스트해보기.

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

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> loadPostDetail(@PathVariable Long postId) {
        try {
            PostResponseServiceDto postResponseServiceDto = postService.findPost(postId);
            List<ImageResponseDto> imageResponseDtos = imageService.inqueryImagesUsingPostId(postId);
            PostDetailResponseDto postDetailResponseDto = PostDetailResponseDto.builder()
                    .postResponseServiceDto(postResponseServiceDto)
                    .likeNumberCnt(getLikeNumberCnt(postResponseServiceDto))
                    .imageResponseDtos(imageResponseDtos)
                    .build();
            return ResponseEntity.ok(postDetailResponseDto);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            imageService.deleteImagesThisPost(postId);
            return ResponseEntity.ok("delete complete");
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<String> editPost(@PathVariable Long postId,
                                           @RequestParam(defaultValue = TEMP_USER_ID) Long userId,
                                           @ModelAttribute PostEditRequestDto form) {
        //TODO 검증 -> View에서도 검증 해줘야함.
        try {
            validatePostForm(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            postService.update(postId, form.getTitle(), form.getWriter(),
                    form.getContent(), form.getCategory());
            imageService.updateImageInfos(postId, userId, form.getImageInfos());
            return ResponseEntity.ok("update complete");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createPost(@RequestParam(defaultValue = TEMP_USER_ID) Long userId,
                                             @ModelAttribute PostCreateRequestDto form) {
        //TODO 검증 -> View에서도 검증 해줘야함.
        try {
            validatePostForm(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            Long savedId = postService.save(form.getTitle(), form.getWriter(), form.getContent(), form.getCategory());
            imageService.uploadImagesThisPost(savedId, userId, form.getImageRequestDtos());
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
