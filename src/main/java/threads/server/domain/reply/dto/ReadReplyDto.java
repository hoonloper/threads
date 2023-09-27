package threads.server.domain.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReadReplyDto {
    Integer totalPage;
    Long totalElement;
    List<ReplyDto> items;
}
