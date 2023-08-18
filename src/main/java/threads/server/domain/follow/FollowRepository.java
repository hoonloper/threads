package threads.server.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import threads.server.model.Follow;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByToUserIdAndFromUserId(Long toUserId, Long fromUserId);
}
