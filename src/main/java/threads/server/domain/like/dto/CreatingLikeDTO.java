package threads.server.domain.like.dto;

import threads.server.domain.like.LikeType;

public record CreatingLikeDTO(
        Long userId,
        Long targetId,
        LikeType type
) {}
