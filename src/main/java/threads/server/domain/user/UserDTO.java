package threads.server.domain.user;

public record UserDTO(
        Long id,
        String email,
        String name,
        String nickname,
        UserRole userRole
) {
}
