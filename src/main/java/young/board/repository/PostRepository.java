package young.board.repository;

import young.board.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    public Long save(Post post);

    public Optional<Post> findOne(Long postId);

    public List<Post> findAll();

    //update는 서비스 계층에서 해주는 게 맞는듯. 이거 관련해서 찾아보기.

    public void delete(Post post); //서비스 계층에서 postId를 통해 post 찾아오기. 그걸 넘겨주기

    //좋아요 누른건 repository 탈 필요 없는거같음 -> 좋아요 누르면 변경사항이 dirty checking 되어 자동으로 update쿼리 날아갈듯

}
