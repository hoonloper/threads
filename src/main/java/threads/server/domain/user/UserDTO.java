package threads.server.domain.user;

import threads.server.model.User;

import java.time.LocalDateTime;

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
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getNickname(),
                user.getUserRole(),
                user.getCreatedAt(),
                user.getLastModifiedAt()
        );
    }
}
