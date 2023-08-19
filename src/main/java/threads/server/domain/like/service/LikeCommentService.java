package threads.server.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.domain.comment.Comment;
import threads.server.domain.like.LikeDTO;
import threads.server.domain.like.entity.LikeComment;
import threads.server.domain.like.repository.LikeCommentRepository;
import threads.server.domain.user.User;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class LikeCommentService implements LikeService {
    private final LikeCommentRepository likeCommentRepository;

    public void save(LikeDTO likeDTO) {
        User user = new User(likeDTO.userId());
        Comment comment = new Comment(likeDTO.targetId());
        likeCommentRepository.save(new LikeComment(null, user, comment, LocalDateTime.now()));
    }

    public void delete(LikeDTO likeDTO) {
        LikeComment likeComment = likeCommentRepository.findById(likeDTO.id()).orElseThrow();
        likeCommentRepository.delete(likeComment);
    }
}
