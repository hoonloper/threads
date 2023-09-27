package threads.server.domain.comment.dto;

import lombok.Data;
import threads.server.domain.comment.Comment;
import threads.server.domain.user.dto.UserDTO;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private Long id;
    private UserDTO user;
    private Long postId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Boolean liked;
    private Integer likeCount;

    public CommentDTO(Long id, UserDTO user, Long postId, String content, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.user = user;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static CommentDTO toCommentDto(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                UserDTO.toDto(comment.getUser()),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getLastModifiedAt()
        );
    }
}
