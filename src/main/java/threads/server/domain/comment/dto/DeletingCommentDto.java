package threads.server.domain.comment.dto;

public record DeletingCommentDto(
        Long id,
        Long userId
) {}
