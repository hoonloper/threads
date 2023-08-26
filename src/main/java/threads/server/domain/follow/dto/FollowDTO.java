package threads.server.domain.follow.dto;

import java.time.LocalDateTime;

public record FollowDTO(Long id, Long toUserId, Long fromUserId, LocalDateTime followAt) {
}
