package threads.server.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threads.server.domain.like.entity.LikeComment;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long>, LikeRepository<LikeComment> {
}
