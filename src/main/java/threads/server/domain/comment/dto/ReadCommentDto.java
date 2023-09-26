package threads.server.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReadCommentDto {
    Integer totalPage;
    Long totalElement;
    List<CommentDTO> items;
}
