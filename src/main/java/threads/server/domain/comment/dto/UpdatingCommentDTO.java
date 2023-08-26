package threads.server.domain.comment.dto;

public record UpdatingCommentDTO(
        Long id,
        Long userId,
        Long postId,
        String content
) {}
