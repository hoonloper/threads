package threads.server.domain.reply.dto;

import lombok.Data;

@Data
public class CreatingReplyDto {
    private Long userId;
    private Long commentId;
    private String content;
}
