package threads.server.domain.like.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import threads.server.domain.like.LikeType;

@Getter
public class CreatingLikeDto {
    @NotNull(message = "userId is null")
    @Positive
    private Long userId;

    @NotNull(message = "targetId is null")
    @Positive
    private Long targetId;

    @NotNull(message = "targetUserId is null")
    @Positive
    private Long targetUserId;

    // TODO: ENUM 타입 검증
    private LikeType type;
}
