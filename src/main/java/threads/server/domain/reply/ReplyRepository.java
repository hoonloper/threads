package threads.server.domain.reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Integer countByCommentId(Long commentId);

    @Query("SELECT r FROM Reply r INNER JOIN FETCH r.user WHERE r.comment.id = :commentId")
    Page<Reply> findAllReplies(Pageable pageable, Long commentId);
}
