package threads.server.domain.activity.dto;

import lombok.Data;
import threads.server.domain.activity.ActivityStatus;

@Data
public class SaveActivityDto {
    private Long toUserId;
    private Long fromUserId;
    private Long targetId;
    private String content;
    private ActivityStatus status;
}
