package threads.server.domain.post.dto;

import threads.server.domain.comment.dto.CommentDTO;
import threads.server.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public record ReadPostDTO(
        Long id,
        User user,
        String content,
        List<CommentDTO> comments,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {}
