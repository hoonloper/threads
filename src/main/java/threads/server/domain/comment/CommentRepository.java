package threads.server.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import threads.server.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
