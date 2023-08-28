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
public class CommentBulk {
    private final JdbcTemplate jdbcTemplate;


    @Transactional
    public void bulkInsert(int batchSize) {
        for(int i = 1; i <= batchSize; i++) {
            batchInsert(batchSize, i);
        }
    }

    private void batchInsert(int batchSize, int userId) {
        String sql = "INSERT INTO comments (user_id, post_id, content, created_at, last_modified_at) values ( ?, ?, ?, ?, ? )";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, userId);
                ps.setLong(2, (i + 1));
                ps.setString(3, "테스트" + (i + 1));
                ps.setObject(4, LocalDateTime.now());
                ps.setObject(5, LocalDateTime.now());
            }

            @Override
            public int getBatchSize() {
                return batchSize;
            }
        });
    }
}
