package threads.server.domain.user.dto;

import threads.server.domain.user.UserRole;

public record SignUpDTO(
        String email,
        String password,
        String name,
        String nickname,
        UserRole userRole
) {}
