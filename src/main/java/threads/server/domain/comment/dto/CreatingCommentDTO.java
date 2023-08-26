package threads.server.domain.comment.dto;

public record CreatingCommentDTO(
        Long userId,
        Long postId,
        String content
) {}
