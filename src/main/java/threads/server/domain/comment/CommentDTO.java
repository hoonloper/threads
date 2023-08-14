package threads.server.domain.comment;

import java.time.LocalDateTime;

public record CommentDTO(
        Long id,
        Long userId,
        Long postId,
        String content,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {
}
