package threads.server.domain.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import threads.server.domain.activity.ActivityStatus;

@Data
@AllArgsConstructor
public class SaveActivityDto {
    private Long toUserId;
    private Long fromUserId;
    private Long targetId;
    private String content;
    private ActivityStatus status;
}
