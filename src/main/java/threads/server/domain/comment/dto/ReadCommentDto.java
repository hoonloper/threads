package threads.server.domain.comment.dto;

import java.util.List;

public record ReadCommentDto(
        Integer totalPage,
        Long totalElement,
        List<CommentDto> items
) {}
