package threads.server.domain.like.dto;

import lombok.Data;
import threads.server.domain.like.LikeType;

@Data
public class CreatingLikeDto {
    private Long userId;
    private Long targetId;
    private LikeType type;
}
