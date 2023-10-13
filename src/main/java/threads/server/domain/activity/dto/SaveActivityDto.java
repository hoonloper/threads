package threads.server.domain.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import threads.server.domain.activity.ActivityStatus;

@AllArgsConstructor
@Getter
public class SaveActivityDto {
    private Long toUserId;
    private Long fromUserId;
    private Long targetId;
    private String content;
    private ActivityStatus status;

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }
}
