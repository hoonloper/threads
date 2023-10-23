package threads.server.domain.reply.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import threads.server.domain.reply.Reply;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
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

    public void changeUserToUserDto() {
        if(userEntity == null) {
            throw new NullPointerException("userEntity is null");
        }
        user = UserDto.toDto(userEntity);
    }

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
}
