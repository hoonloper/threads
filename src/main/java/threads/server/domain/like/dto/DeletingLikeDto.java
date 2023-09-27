package threads.server.domain.like.dto;

import lombok.Data;
import threads.server.domain.like.LikeType;

@Data
public class DeletingLikeDto {
    private Long userId;
    private Long targetId;
    private LikeType type;
}
