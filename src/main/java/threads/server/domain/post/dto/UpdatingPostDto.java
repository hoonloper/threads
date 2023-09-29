package threads.server.domain.post.dto;

public record UpdatingPostDto(
        Long id,
        Long userId,
        String content
) {}
