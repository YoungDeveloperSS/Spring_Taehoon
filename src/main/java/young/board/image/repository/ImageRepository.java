package young.board.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import young.board.image.Image;
import young.board.post.Post;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByPost(Post post);
}
