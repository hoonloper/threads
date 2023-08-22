package threads.server.domain.user;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDTO(
        Long id,
        String email,
        String password,
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
