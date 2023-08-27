package threads.server.domain.user.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import threads.server.domain.user.UserRole;

public record SignUpDTO(
        @Email
        @NotEmpty
        @NotBlank
        String email,

        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$")
        @NotEmpty
        @NotBlank
        @Length(min = 6, max = 30)
        String password,

        @NotEmpty
        @NotBlank
        @Length(min = 2, max = 10)
        String name,

        @Nullable
        @NotBlank
        @Length(min = 2, max = 10)
        String nickname,

        UserRole userRole
) {}
