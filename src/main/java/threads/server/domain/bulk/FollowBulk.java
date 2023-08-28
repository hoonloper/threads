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
public class FollowBulk {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void bulkInsert(int batchSize) {
        batchInsert(batchSize);
    }

    private void batchInsert(int batchSize) {
        String sql = "INSERT INTO follows (from_user_id, to_user_id, follow_at) values ( ?, ?, ? )";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, 1);
                ps.setLong(2,  (i + 2));
                ps.setObject(3, LocalDateTime.now());
            }

            @Override
            public int getBatchSize() {
                return batchSize;
            }
        });
    }
}
