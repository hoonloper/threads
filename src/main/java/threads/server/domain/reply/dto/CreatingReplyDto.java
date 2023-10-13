package threads.server.domain.reply.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreatingReplyDto {
    @NotNull(message = "userId is null")
    @Positive
    private Long userId;

    @NotNull(message = "commentId is null")
    @Positive
    private Long commentId;

    @NotNull(message = "commentUserId is null")
    @Positive
    private Long commentUserId;

    @NotBlank(message = "content is required")
    @Size(min = 1, max = 500, message = "content length greater than 500")
    private String content;
}
