package threads.server.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threads.server.domain.like.entity.LikeComment;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long>, LikeRepository<LikeComment> {
    Optional<LikeComment> findByUserIdAndCommentId(Long userId, Long commentId);

    Long countByCommentId(Long id);
}
