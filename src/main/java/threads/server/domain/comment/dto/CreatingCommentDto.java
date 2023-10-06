package threads.server.domain.comment.dto;

public record CreatingCommentDto(
        Long userId,
        Long postId,
        String content
) {}
