package threads.server.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import threads.server.domain.comment.Comment;
import threads.server.domain.reply.Reply;
import threads.server.domain.reply.dto.ReplyDto;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @JsonIgnore
    private User userEntity;
    private UserDto user;

    private Boolean liked;
    private Long likeCount;

    @JsonIgnore
    private Reply replyEntity;
    private ReplyDto reply;


    @QueryProjection
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
//                UserDto.toDto(comment.getUser()),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getLastModifiedAt()
        );
    }
}
