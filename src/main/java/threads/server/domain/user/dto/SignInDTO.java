package threads.server.domain.user.dto;

public record SignInDTO(
        String email,
        String password
) {}
