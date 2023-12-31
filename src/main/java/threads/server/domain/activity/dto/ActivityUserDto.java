package threads.server.domain.activity.dto;

import lombok.Builder;
import lombok.Getter;
import threads.server.domain.user.User;

@Builder
@Getter
public class ActivityUserDto {
    private Long id;
    private String name;
    private String nickname;

    public static ActivityUserDto toActivityUserDto(User user) {
        return ActivityUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .build();
    };
}
