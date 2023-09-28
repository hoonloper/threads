package threads.server.domain.reply.dto;

import lombok.Builder;
import lombok.Data;
import threads.server.domain.reply.Reply;
import threads.server.domain.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
public class ReplyDto {

    private Long id;
    private UserDto user;
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Boolean liked;
    private Integer likeCount;

    @Builder
    public ReplyDto(Long id, UserDto user, Long commentId, String content, LocalDateTime createdAt, LocalDateTime lastModifiedAt, Boolean liked, Integer likeCount) {
        this.id = id;
        this.user = user;
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.liked = liked;
        this.likeCount = likeCount;
    }

    public static ReplyDto toReplyDto(Reply reply) {
        return ReplyDto
                .builder()
                .id(reply.getId())
                .user(UserDto.toDto(reply.getUser()))
                .commentId(reply.getComment().getId())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .lastModifiedAt(reply.getLastModifiedAt())
                .build();
    }
}
