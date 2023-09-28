package threads.server.domain.reply.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import threads.server.domain.reply.Reply;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Integer countByCommentId(Long commentId);
    Optional<Reply> findOneByCommentIdOrderByCreatedAtAsc(Long commentId);

    @Query("SELECT r FROM Reply r INNER JOIN FETCH r.user WHERE r.comment.id = :commentId")
    Page<Reply> findAllReplies(Pageable pageable, Long commentId);
}
