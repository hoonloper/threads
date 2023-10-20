package threads.server.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import threads.server.domain.comment.Comment;
import threads.server.domain.reply.Reply;
import threads.server.domain.reply.dto.ReplyDto;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class CommentDto {

    private Long id;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Boolean liked;
    private Long likeCount;
    private Long replyCount;

    @JsonIgnore
    private User userEntity;
    private UserDto user;
    public void changeUserToUserDto() {
        if(userEntity == null) {
            throw new NullPointerException("userEntity is null");
        }
        user = UserDto.toDto(userEntity);
    }


    @JsonIgnore
    private Reply replyEntity;
    private List<ReplyDto> replies = new ArrayList<>();

    public CommentDto(Long id, Long postId, String content, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getLastModifiedAt()
        );
    }

    public void addReply(final ReplyDto replyDto) {
        replies.add(replyDto);
    }
}
