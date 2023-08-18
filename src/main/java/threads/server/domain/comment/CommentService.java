package threads.server.domain.comment;

import org.springframework.stereotype.Service;
import threads.server.application.exceptions.NotFoundException;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

import static threads.server.domain.comment.CommentDTO.toCommentDto;

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

    public CommentDTO update(CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentDTO.id()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        comment.change(commentDTO.content());
        commentRepository.save(comment);
        return toCommentDto(comment);
    }

    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }
}
