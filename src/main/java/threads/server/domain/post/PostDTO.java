package threads.server.domain.post;

import java.time.LocalDateTime;

public record PostDTO(
        Long id,
        Long userId,
        String content,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {
}
