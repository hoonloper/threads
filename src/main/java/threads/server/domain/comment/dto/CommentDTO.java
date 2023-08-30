package threads.server.domain.comment.dto;

import threads.server.domain.comment.Comment;
import threads.server.domain.user.dto.UserDTO;

import java.time.LocalDateTime;

public record CommentDTO(
        Long id,
        UserDTO user,
        Long postId,
        String content,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {
    public static CommentDTO toCommentDto(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                UserDTO.toDto(comment.getUser()),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getLastModifiedAt()
        );
    }
}
