package threads.server.domain.reply.dto;

import org.springframework.data.domain.Page;
import threads.server.domain.reply.Reply;

import java.util.List;

public class ReadReplyDto {
    private final Integer totalPage;
    private final Long totalElement;
    private final List<ReplyDto> items;

    private ReadReplyDto(final Page<Reply> page, final List<ReplyDto> items) {
        this.totalPage = page.getTotalPages();
        this.totalElement = page.getTotalElements();
        this.items = items;
    }

    static public ReadReplyDto toReadReplyDto(final Page<Reply> page, final List<ReplyDto> items) {
        return new ReadReplyDto(page, toUserDtoInReplies(items));
    }

    static private List<ReplyDto> toUserDtoInReplies(final List<ReplyDto> items) {
        return items.stream().peek(ReplyDto::changeUserToUserDto).toList();
    }
}
