package young.board.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.board.image.repository.ImageRepository;
import young.board.post.Post;
import young.board.post.repository.PostRepository;
import young.board.user.UserRepository;
import young.board.user.User;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

import static young.board.message.ErrorMessage.NOT_EXIST_POST_ERROR;
import static young.board.message.ErrorMessage.NOT_EXIST_USER_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 생성된 이미지들의 URI들을 저장.
     */
    @Transactional
    public Long uploadImagesThisPost(Long postId, Long userId, List<ImageRequestDto> imageRequestDtos) {
        //imageInfo가 아니라 requestServiceDTO가 더 괜찮은 이름일까? 그리고 저 세개를 묶어서 DTO로 만들어버려도 괜찮음.
        Post post = validatePost(postId);
        User user = validateUser(userId);
        uploadEachImage(imageRequestDtos, post, user);
        return post.getId();
    }

    private void uploadEachImage(List<ImageRequestDto> imageRequestDtos, Post post, User user) {
        for (ImageRequestDto imageRequestDto : imageRequestDtos) {
            Image image = Image.builder()
                    .post(post)
                    .user(user)
                    .uri(imageRequestDto.getUri())
                    .order(imageRequestDto.getOrder())
                    .build();
            imageRepository.save(image);
            //TODO JPA가 image객체를 한꺼번에 쿼리를 보내 save할까, 일일히 insert를 해줄까?
            // 후자라고 한다면 로직을 변경하기.
        }
    }

    private User validateUser(Long userId) {
        Optional<User> parsingUser = userRepository.findOne(userId);
        if (parsingUser.isEmpty()) {
            throw new IllegalArgumentException(NOT_EXIST_USER_ERROR);
        }
        User user = parsingUser.get();
        return user;
    }

    private Post validatePost(Long postId) {
        Optional<Post> parsingPost = postRepository.findOne(postId);
        if (parsingPost.isEmpty()) {
            throw new IllegalArgumentException(NOT_EXIST_POST_ERROR);
        }
        Post post = parsingPost.get();
        return post;
    }

    /**
     * 이미지들 조회
     */
    public List<ImageResponseDto> inqueryImagesUsingPostId(Long postId) { //image -> responseDto로
        Post post = validatePost(postId);
        List<Image> images = imageRepository.findAllByPost(post);
        return images.stream().map(image -> ImageResponseDto.of(image)).collect(Collectors.toList());
    }

    /**
     * 이미지들의 정보 변경 -> delete, update가 있던 경우 처리되는 로직.
     * 이름을 뭘로 지어야 할지 모르겠음.
     */
    //TODO 고민하던 부분. 여기 더 손봐야함!! 로직 고민해봐야함. upload하는 순간 이미지 crud가 다 가능.
    @Transactional
    public Long updateImageInfos(Long postId, Long userId, List<ImageUpdateRequestDto> imageInfos) {
        Post post = validatePost(postId);
        User user = validateUser(userId);
        // TODO userId를 사용해 작성자가 동일한지 확인한다. 권한 확인
        //  post에 id값(지금은 없음) != userId => 예외 터침.
        List<Image> images = imageRepository.findAllByPost(post);

        for (Image image : images) {
            OptionalLong any = imageInfos.stream().mapToLong(imageInfo -> imageInfo.getId())
                    .filter(imageInfoId -> imageInfoId == image.getId())
                    .findAny();
            if (any.isEmpty()) {
                System.out.println("들어옴~");
                imageRepository.delete(image);
            }
        }
        for (ImageUpdateRequestDto imageInfo : imageInfos) {
            // imageInfo가 images에 있던 값이면(id가 있음)
            // Repository에서 update를 해줌.
            // 없던 값이면
            // create를 해줌.
            if (alreadyExist(imageInfo.getId(), images)) {
                Image image = imageRepository.findById(imageInfo.getId()).get();
                image.changeOrder(imageInfo.getOrder());
            } else {
                Image image = Image.builder()
                        .post(post)
                        .user(user)
                        .uri(imageInfo.getUri())
                        .order(imageInfo.getOrder())
                        .build();
                imageRepository.save(image);
            }
        }

        return postId;
    }

    private boolean alreadyExist(Long targetImageId, List<Image> images) {
        return images.stream().anyMatch(image -> image.getId() == targetImageId);

    }

    /**
     * 이미지들 삭제
     */
    @Transactional
    public void deleteImagesThisPost(Long postId) {
        Post post = validatePost(postId);
        imageRepository.deleteAllByPost(post);
    }
}
