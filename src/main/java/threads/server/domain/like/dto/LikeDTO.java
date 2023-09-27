package threads.server.domain.like.dto;

import threads.server.domain.like.LikeType;

import java.time.LocalDateTime;

public class LikeDTO {
    private Long id;
    private Long userId;
    private Long targetId;
    private LikeType type;
    private LocalDateTime likeAt;
}
