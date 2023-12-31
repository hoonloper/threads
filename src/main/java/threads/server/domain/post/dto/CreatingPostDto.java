package threads.server.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreatingPostDto {
    @NotNull(message = "userId is null")
    @Positive
    private Long userId;

    @NotBlank(message = "content is required")
    @Size(min = 1, max = 500, message = "content length greater than 500")
    private String content;
}
