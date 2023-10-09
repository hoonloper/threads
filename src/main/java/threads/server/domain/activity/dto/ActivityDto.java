package threads.server.domain.activity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import threads.server.domain.activity.ActivityStatus;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
public class ActivityDto {
    private Long id;
    private Long toUserId;

    @JsonIgnore
    private User fromUserEntity;
    private UserDto fromUser;

    private Long targetId;
    private String content;
    private ActivityStatus status;
    private Boolean isConfirmed;
    private LocalDateTime issuedAt;
}
