package young.board.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import young.board.image.repository.ImageRepository;
import young.board.post.Post;
import young.board.post.repository.PostRepository;
import young.board.user.UserRepository;
import young.board.user.Users;

import java.util.List;
import java.util.Optional;

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
    public Long uploadImagesThisPost(Long postId, Long userId, List<ImageInfo> imageInfos) {
        //imageInfo가 아니라 requestServiceDTO가 더 괜찮은 이름일까? 그리고 저 세개를 묶어서 DTO로 만들어버려도 괜찮음.
        Post post = validatePost(postId);
        Users user = validateUser(userId);
        saveEachImage(imageInfos, post, user);
        return post.getId();
    }

    private void saveEachImage(List<ImageInfo> imageInfos, Post post, Users user) {
        for (ImageInfo imageInfo : imageInfos) {
            Image image = Image.builder()
                    .post(post)
                    .user(user)
                    .uri(imageInfo.getUri())
                    .order(imageInfo.getOrder())
                    .build();
            imageRepository.save(image);
        }
    }

    private Users validateUser(Long userId) {
        Optional<Users> parsingUser = userRepository.findOne(userId);
        if (parsingUser.isEmpty()) {
            throw new IllegalArgumentException(NOT_EXIST_USER_ERROR);
        }
        Users user = parsingUser.get();
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
    public List<Image> inqueryImagesUsingPostId(Long postId) { //image -> responseDto로
        Post post = validatePost(postId);
        List<Image> images = imageRepository.findAllByPost(post);
        return images;
    }

    /**
     * 이미지들의 정보 변경 -> delete, update가 있던 경우 처리되는 로직.
     * 이름을 뭘로 지어야 할지 모르겠음.
     */
    @Transactional
    public Long updateImageInfos(Long postId, Long userId, List<ImageInfo> imageInfos) {
        Post post = validatePost(postId);
        // TODO userId를 사용해 작성자가 동일한지 확인한다. 권한 확인
        //  post에 id값(지금은 없음) != userId => 예외 터침.

        List<Image> images = imageRepository.findAllByPost(post);
        for (Image image : images) {
            changeImageOrderIfChange(imageInfos, image);
        }
        return postId;
    }

    private void changeImageOrderIfChange(List<ImageInfo> imageInfos, Image image) {
        for (ImageInfo imageInfo : imageInfos) {
            if (isChangeOrder(image, imageInfo)) {
                image.changeOrder(imageInfo.getOrder());
                return;
            }
        }
        deleteImageBecauseNotExistInImageInfos(image); //두번째 포문을 다 돌았음.
    }

    private void deleteImageBecauseNotExistInImageInfos(Image image) {
        imageRepository.delete(image);
    }

    private boolean isChangeOrder(Image image, ImageInfo imageInfo) {
        return image.getId() == imageInfo.getId() && (image.getOrder() != imageInfo.getOrder());
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
