package threads.server.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.comment.dto.CommentDTO;
import threads.server.domain.comment.dto.CreatingCommentDTO;
import threads.server.domain.comment.dto.RemovingCommentDTO;
import threads.server.domain.comment.dto.UpdatingCommentDTO;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

import static threads.server.domain.comment.dto.CommentDTO.toCommentDto;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentDTO save(CreatingCommentDTO commentDto) {
        User user = new User(commentDto.userId());
        Post post = new Post(commentDto.postId());
        return toCommentDto(commentRepository.save(new Comment(null, user, post, commentDto.content())));
    }

    public CommentDTO update(UpdatingCommentDTO commentDto) {
        Comment comment = commentRepository.findById(commentDto.id()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        authorizeUser(commentDto.userId(), comment.getUser().getId());
        comment.change(commentDto.content());
        commentRepository.save(comment);
        return toCommentDto(comment);
    }

    public void delete(RemovingCommentDTO commentDto) {
        Comment comment = commentRepository.findById(commentDto.id()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        authorizeUser(commentDto.userId(), comment.getUser().getId());
        commentRepository.delete(comment);
    }


    private void authorizeUser(Long requestUserId, Long userIdFromComment) {
        if (!requestUserId.equals(userIdFromComment)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }
}
