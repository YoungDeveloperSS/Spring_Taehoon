package young.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    public Long save(Post post);

    public Optional<Post> findOne(Long postId);

    public List<Post> findAll();

    public Long update(String title, String writer, String content, Category category);

    public void delete(Long postId);

    //좋아요 누른건 repository 탈 필요 없는거같음 -> 좋아요 누르면 변경사항이 dirty checking 되어 자동으로 update쿼리 날아갈듯

}
