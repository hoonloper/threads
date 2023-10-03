package threads.server.domain.post.dto;

public record CreatingPostDto(
        Long userId,
        String content
) {}
