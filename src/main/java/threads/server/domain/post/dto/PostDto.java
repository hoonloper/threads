package threads.server.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PostDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private Long likeCount;
    private Long commentCount;
    private Long userId;
    private Boolean liked;


    @JsonIgnore
    private User userEntity;
    private UserDto user;

    public PostDto(Long id, UserDto user, String content, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static PostDto toPostDto(Post post) {
        return new PostDto(
                post.getId(),
                UserDto.toDto(post.getUser()),
                post.getContent(),
                post.getCreatedAt(),
                post.getLastModifiedAt()
        );
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}
