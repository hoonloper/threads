package threads.server.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByToUserIdAndFromUserId(Long toUserId, Long fromUserId);

    @Query("SELECT f.toUser.id FROM Follow f WHERE f.fromUser.id = :fromUserId")
    List<Long> findAllByFollowingIds(@Param("fromUserId") Long fromUserId);

    Long countByToUserId(Long toUserId);
}
