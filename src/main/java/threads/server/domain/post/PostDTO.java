package threads.server.domain.post;

import threads.server.domain.comment.CommentDTO;
import threads.server.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record PostDTO(
        Long id,
        Long userId,
        String content,
        List<CommentDTO> comments,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt
) {
    public static PostDTO toPostDto(Post post) {
        return new PostDTO(
                post.getId(),
                post.getUser().getId(),
                post.getContent(),
                post.getComments().stream().map(CommentDTO::toCommentDto).collect(Collectors.toList()),
                post.getCreatedAt(),
                post.getLastModifiedAt()
        );
    }
}
