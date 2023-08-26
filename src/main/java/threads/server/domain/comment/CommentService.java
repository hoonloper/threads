package threads.server.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.domain.comment.dto.CommentDTO;
import threads.server.domain.comment.dto.CreatingCommentDTO;
import threads.server.domain.comment.dto.UpdatingCommentDTO;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

import static threads.server.domain.comment.dto.CommentDTO.toCommentDto;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentDTO save(CreatingCommentDTO commentDTO) {
        User user = new User(commentDTO.userId());
        Post post = new Post(commentDTO.postId());
        return toCommentDto(commentRepository.save(new Comment(null, user, post, commentDTO.content())));
    }

    public CommentDTO update(UpdatingCommentDTO commentDTO) {
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
