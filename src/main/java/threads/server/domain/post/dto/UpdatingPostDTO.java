package threads.server.domain.post.dto;

public record UpdatingPostDTO(
        Long id,
        Long userId,
        String content
) {}
