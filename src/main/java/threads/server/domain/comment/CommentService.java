package threads.server.domain.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.comment.dto.*;
import threads.server.domain.like.repository.LikeCommentRepository;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

import java.util.List;

import static threads.server.domain.comment.dto.CommentDTO.toCommentDto;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;

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

    public void delete(DeletingCommentDTO commentDto) {
        Comment comment = commentRepository.findById(commentDto.id()).orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));
        authorizeUser(commentDto.userId(), comment.getUser().getId());
        commentRepository.delete(comment);
    }


    private void authorizeUser(Long requestUserId, Long userIdFromComment) {
        if (!requestUserId.equals(userIdFromComment)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }

    public ReadCommentDto findAllByPostId(Pageable pageable, Long postId, Long userId) {
        Page<Comment> comments = commentRepository.findAllComments(pageable);
        List<CommentDTO> commentList = comments.stream().map(comment -> {
            CommentDTO commentDto = toCommentDto(comment);
            // TODO: commentDto.setReplyCount(replyRepository.countByPostId(comment.getId()));
            commentDto.setLikeCount(likeCommentRepository.countByCommentId(comment.getId()));
            commentDto.setLiked(likeCommentRepository.findByUserIdAndCommentId(userId, comment.getId()).isPresent());
            return commentDto;
        }).toList();
        return new ReadCommentDto(comments.getTotalPages(), comments.getTotalElements(), commentList);
    }
}
