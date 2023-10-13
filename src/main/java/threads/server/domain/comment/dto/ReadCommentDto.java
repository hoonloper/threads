package threads.server.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ReadCommentDto {
    Integer totalPage;
    Long totalElement;
    List<CommentDto> items;
}
