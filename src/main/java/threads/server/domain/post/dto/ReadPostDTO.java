package threads.server.domain.post.dto;

import java.util.List;

public record ReadPostDTO(
        Integer totalPage,
        Long totalElement,
        List<PostDTO> items
) {}