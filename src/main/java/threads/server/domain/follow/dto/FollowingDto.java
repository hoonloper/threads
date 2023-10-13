package threads.server.domain.follow.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class FollowingDto {
    @NotNull(message = "toUserId is null")
    @Positive
    private Long toUserId;

    @NotNull(message = "fromUserId is null")
    @Positive
    private Long fromUserId;
}
