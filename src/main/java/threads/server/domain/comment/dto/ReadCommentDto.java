package threads.server.domain.comment.dto;

import java.util.List;

public record ReadCommentDto(
        Integer totalPage,
        Long totalElement,
        List<CommentDto> items
) {

    public ReadCommentDto(Integer totalPage, Long totalElement, List<CommentDto> items) {
        this.totalPage = totalPage;
        this.totalElement = totalElement;
        this.items = items.stream().peek(CommentDto::changeUserToUserDto).toList();
    }
}
