package threads.server.domain.like.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import threads.server.domain.like.LikeType;

@Data
public class DeletingLikeDto {
    @NotNull(message = "userId is null")
    @Positive
    private Long userId;

    @NotNull(message = "targetId is null")
    @Positive
    private Long targetId;

    private LikeType type;
}
