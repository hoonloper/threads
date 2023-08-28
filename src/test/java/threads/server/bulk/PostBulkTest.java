package threads.server.bulk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import threads.server.domain.post.PostBulk;

//@Transactional // SQL이 롤백이 된다.
@SpringBootTest
class PostBulkTest {
    @Autowired
    private PostBulk postBulk;

    @Test
    void bulkInsert() {
        int SIZE = 100_000;
//        postBulk.bulkInsert(SIZE);
    }
}
