package threads.server.domain.post.dto;

import threads.server.domain.comment.dto.CommentDTO;
import threads.server.domain.post.Post;
import threads.server.domain.user.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PostDTO(
        Long id,
        UserDTO user,
        String content,
        List<CommentDTO> comments,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {
    public static PostDTO toPostDto(Post post) {
        return new PostDTO(
                post.getId(),
                UserDTO.toDto(post.getUser()),
                post.getContent(),
                post.getComments().stream().map(CommentDTO::toCommentDto).collect(Collectors.toList()),
                post.getCreatedAt(),
                post.getLastModifiedAt()
        );
    }
}
