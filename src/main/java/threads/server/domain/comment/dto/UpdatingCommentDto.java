package threads.server.domain.comment.dto;

public record UpdatingCommentDto(
        Long id,
        Long userId,
        Long postId,
        String content
) {}
