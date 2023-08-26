package threads.server.domain.post.dto;

public record CreatingPostDTO(
        Long userId,
        String content
) {}
