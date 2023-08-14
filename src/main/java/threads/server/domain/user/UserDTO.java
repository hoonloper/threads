package threads.server.domain.user;

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
}
