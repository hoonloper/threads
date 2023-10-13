package threads.server.domain.reply.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.reply.Reply;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ReplyDto {

    private Long id;
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Boolean liked;
    private Long likeCount;

    @JsonIgnore
    private User userEntity;
    private UserDto user;

    @Builder
    public ReplyDto(Long id, Long commentId, String content, LocalDateTime createdAt, LocalDateTime lastModifiedAt, UserDto user) {
        this.id = id;
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.user = user;
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

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
