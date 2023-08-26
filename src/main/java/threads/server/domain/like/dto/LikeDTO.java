package threads.server.domain.like.dto;

import threads.server.domain.like.LikeType;

import java.time.LocalDateTime;

public record LikeDTO(
        Long id,
        Long userId,
        Long targetId,
        LikeType type,
        LocalDateTime likeAt
) {
}
