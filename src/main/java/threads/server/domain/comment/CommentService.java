package threads.server.domain.comment;

import org.springframework.stereotype.Service;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDTO save(CommentDTO commentDTO) {
        User user = new User(commentDTO.userId());
        Post post = new Post(commentDTO.postId());
        return toCommentDto(commentRepository.save(new Comment(null, user, post, commentDTO.content())));
    }

    private CommentDTO toCommentDto(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getUser().getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getLastModifiedAt()
        );
    }
}
