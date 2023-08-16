package threads.server.domain.like;

import java.time.LocalDateTime;

public record LikeDTO(
        Long id,
        Long userId,
        Long targetId,
        LikeType type,
        LocalDateTime likeAt
) {
}
