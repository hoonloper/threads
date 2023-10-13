package threads.server.domain.user.dto;

import lombok.*;
import threads.server.domain.user.User;
import threads.server.domain.user.UserRole;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private String link;
    private String introduction;
    private Boolean isHidden;
    private UserRole userRole;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    private Long followerCount;
    private Long followingCount;
    private Boolean followed;

    @Builder
    public UserDto(Long id, String email, String name, String nickname, String link, String introduction, Boolean isHidden, UserRole userRole, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.link = link;
        this.introduction = introduction;
        this.isHidden = isHidden;
        this.userRole = userRole;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .link(user.getLink())
                .introduction(user.getIntroduction())
                .isHidden(user.isHidden())
                .userRole(user.getUserRole())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .build();
    }
}
