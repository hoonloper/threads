package threads.server.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatingCommentDto {
    @NotNull(message = "id is null")
    @Positive
    private Long id;

    @NotNull(message = "userId is null")
    @Positive
    private Long userId;

    @NotNull(message = "postId is null")
    @Positive
    private Long postId;

    @NotBlank(message = "content is required")
    @Size(min = 1, max = 500, message = "content length greater than 500")
    private String content;
}
