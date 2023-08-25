package threads.server.domain.user.dto;

import lombok.Builder;
import threads.server.domain.user.User;
import threads.server.domain.user.UserRole;

import java.time.LocalDateTime;

@Builder
public record UserDTO(
        Long id,
        String email,
        String name,
        String nickname,
        UserRole userRole,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {
    public static UserDTO toDto(User user) {
        return UserDTO.builder()
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
