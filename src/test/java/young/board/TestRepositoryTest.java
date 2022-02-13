package young.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestRepositoryTest {
    @Autowired
    TestRepository testRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void 테스트() {
        young.board.Test test = new young.board.Test();
        test.setName("das");
        testRepository.save(test);
    }

}