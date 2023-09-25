package threads.server.domain.bulk;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class LikeBulk {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void bulkInsert(int batchSize) {
        batchInsert(batchSize);
    }

    private void batchInsert(int batchSize) {
        String postSql = "INSERT INTO likes_post (user_id, post_id, like_at) values ( ?, ?, ? )";
        String commentSql = "INSERT INTO likes_comment (user_id, commment_id, like_at) values ( ?, ?, ? )";

        BatchPreparedStatementSetter bpss = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, (i + 1));
                ps.setLong(2,  1L);
                ps.setObject(3, LocalDateTime.now());
            }

            @Override
            public int getBatchSize() {
                return batchSize;
            }
        };
        jdbcTemplate.batchUpdate(postSql, bpss);
        jdbcTemplate.batchUpdate(commentSql, bpss);
    }
}
