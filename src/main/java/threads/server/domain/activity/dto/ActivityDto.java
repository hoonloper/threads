package threads.server.domain.activity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.activity.ActivityStatus;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

@Data
public class ActivityDto {
    private Long id;
    private Long toUserId;

    @JsonIgnore
    private User fromUserEntity;
    private ActivityUserDto fromUser;

    private Long targetId;
    private String content;
    private ActivityStatus status;
    private Boolean isConfirmed;
    private Boolean followed;
    private LocalDateTime issuedAt;

    public void setFromUser(ActivityUserDto fromUser) {
        this.fromUser = fromUser;
    }
}
