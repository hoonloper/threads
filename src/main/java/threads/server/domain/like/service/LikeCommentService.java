package threads.server.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.comment.Comment;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.DeletingLikeDto;
import threads.server.domain.like.entity.LikeComment;
import threads.server.domain.like.repository.LikeCommentRepository;
import threads.server.domain.user.User;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class LikeCommentService implements LikeService {
    private final LikeCommentRepository likeCommentRepository;

    public void save(CreatingLikeDto likeDto) {
        User user = new User(likeDto.getUserId());
        Comment comment = new Comment(likeDto.getTargetId());
        likeCommentRepository.save(new LikeComment(null, user, comment, LocalDateTime.now()));
    }

    public void delete(DeletingLikeDto likeDto) {
        LikeComment likeComment = likeCommentRepository.findByUserIdAndCommentId(likeDto.getUserId(), likeDto.getTargetId()).orElseThrow();
        authorizeUser(likeDto.getUserId(), likeComment.getUser().getId());
        likeCommentRepository.delete(likeComment);
    }

    private void authorizeUser(Long requestUserId, Long userIdFromLike) {
        if(!requestUserId.equals(userIdFromLike)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }
}
