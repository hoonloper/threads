package threads.server.domain.post.dto;

import lombok.Data;
import threads.server.domain.post.Post;
import threads.server.domain.user.dto.UserDTO;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Long id;
    private UserDTO user;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Integer likeCount;
    private Integer commentCount;

    public PostDTO(Long id, UserDTO user, String content, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static PostDTO toPostDto(Post post) {
        return new PostDTO(
                post.getId(),
                UserDTO.toDto(post.getUser()),
                post.getContent(),
                post.getCreatedAt(),
                post.getLastModifiedAt()
        );
    }
}
