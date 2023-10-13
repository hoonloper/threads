package threads.server.domain.reply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ReadReplyDto {
    Integer totalPage;
    Long totalElement;
    List<ReplyDto> items;
}
