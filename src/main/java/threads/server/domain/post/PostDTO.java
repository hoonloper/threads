package threads.server.domain.post;

import threads.server.domain.comment.CommentDTO;

import java.time.LocalDateTime;
import java.util.List;

public record PostDTO(
        Long id,
        Long userId,
        String content,
        List<CommentDTO> comments,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {
}
