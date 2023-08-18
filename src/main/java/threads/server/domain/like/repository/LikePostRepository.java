package threads.server.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threads.server.domain.like.entity.LikePost;

public interface LikePostRepository extends JpaRepository<LikePost, Long>, LikeRepository<LikePost> {
}
