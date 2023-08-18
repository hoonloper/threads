package threads.server.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByToUserIdAndFromUserId(Long toUserId, Long fromUserId);
}
