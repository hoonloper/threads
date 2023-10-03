package threads.server.domain.post.dto;

import java.util.List;

public record ReadPostDto(
        Integer totalPage,
        Long totalElement,
        List<PostDto> items
) {}