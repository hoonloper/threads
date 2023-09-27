package threads.server.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threads.server.domain.like.entity.LikePost;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long>, LikeRepository<LikePost> {
    Integer countByPostId(Long postId);

    Optional<LikePost> findByUserIdAndPostId(Long userId, Long postId);
}
