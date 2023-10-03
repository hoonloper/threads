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
public class UserBulk {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void bulkInsert(int batchSize) {
        batchInsert(batchSize);
    }

    private void batchInsert(int batchSize) {
        String sql = "INSERT INTO users (email, password, name, nickname, link, introduction, is_hidden, user_role, created_at, last_modified_at) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, "test" + i + "@test.com");
                ps.setString(2, "password" + i);
                ps.setString(3, "name" + i);
                ps.setString(4, "nickname" + i);
                ps.setString(5, "https://naver.com");
                ps.setString(6, "내 자기소개는 몇 번째 ? " + (i + 1));
                ps.setBoolean(7, false);
                ps.setString(8, "USER");
                ps.setObject(9, LocalDateTime.now());
                ps.setObject(10, LocalDateTime.now());
            }

            @Override
            public int getBatchSize() {
                return batchSize;
            }
        });
    }
}
