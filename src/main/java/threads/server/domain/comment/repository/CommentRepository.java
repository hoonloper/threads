package threads.server.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threads.server.domain.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
