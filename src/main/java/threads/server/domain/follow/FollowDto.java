package threads.server.domain.follow;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class FollowDto {
    @NotNull(message = "toUserId is null")
    @Positive
    private Long toUserId;

    @NotNull(message = "fromUserId is null")
    @Positive
    private Long fromUserId;

    public Boolean checkIfSelfFollowing() {
        return fromUserId.equals(toUserId);
    }
}
