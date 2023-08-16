package threads.server.domain.like;

import java.time.LocalDateTime;

public record LikeDTO(
        Long id,
        Long userId,
        Long targetId,
        LikeType type,
        LocalDateTime likeAt
) {
    public static LikeDTO toLikeDto(Long id, LikeDTO likeDTO, LocalDateTime likeAt) {
        return new LikeDTO(id, likeDTO.userId(), likeDTO.targetId(), likeDTO.type(), likeAt);
    }
}
