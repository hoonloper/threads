package threads.server.domain.like.dto;

import threads.server.domain.like.LikeType;

public record RemovingLikeDTO(
        Long id,
        Long userId,
        Long targetId,
        LikeType type
) {
}
