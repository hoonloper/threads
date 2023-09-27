package threads.server.domain.reply.dto;

import lombok.Data;
import threads.server.domain.reply.Reply;
import threads.server.domain.user.dto.UserDTO;

import java.time.LocalDateTime;

@Data
public class ReplyDto {

    private Long id;
    private UserDTO user;
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Boolean liked;
    private Integer likeCount;

    public ReplyDto(Long id, UserDTO user, Long commentId, String content, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.user = user;
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static ReplyDto toReplyDto(Reply reply) {
        return new ReplyDto(
                reply.getId(),
                UserDTO.toDto(reply.getUser()),
                reply.getComment().getId(),
                reply.getContent(),
                reply.getCreatedAt(),
                reply.getLastModifiedAt()
        );
    }
}
