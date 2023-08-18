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
    public static CommentDTO toCommentDto(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getLastModifiedAt()
        );
    }
}
