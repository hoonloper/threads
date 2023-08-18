package threads.server.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threads.server.model.LikePost;

public interface LikePostRepository extends JpaRepository<LikePost, Long>, LikeRepository<LikePost> {
}
