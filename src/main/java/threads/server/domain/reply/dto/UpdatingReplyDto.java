package threads.server.domain.reply.dto;

import lombok.Data;

@Data
public class UpdatingReplyDto {
    private Long id;
    private Long userId;
    private Long commentId;
    private String content;
}
