package threads.server.domain.user.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import threads.server.domain.user.User;
import threads.server.domain.user.UserRole;

import java.time.LocalDateTime;

@Builder
@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private UserRole userRole;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @QueryProjection
    public UserDto(Long id, String email, String name, String nickname, UserRole userRole, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
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
                .userRole(user.getUserRole())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .build();
    }
}
