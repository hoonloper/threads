package threads.server.domain.reply.dto;

import java.util.List;

public record ReadReplyDto(
        Integer totalPage,
        Long totalElement,
        List<ReplyDto> items
) {}
