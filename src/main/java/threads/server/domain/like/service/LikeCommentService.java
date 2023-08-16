package threads.server.domain.like.service;

import org.springframework.stereotype.Service;
import threads.server.domain.comment.Comment;
import threads.server.domain.like.LikeDTO;
import threads.server.domain.like.entity.LikeComment;
import threads.server.domain.like.repository.LikeCommentRepository;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

import static threads.server.domain.like.LikeDTO.toLikeDto;

@Service
public class LikeCommentService extends LikeService {
    private final LikeCommentRepository likeCommentRepository;

    public LikeCommentService(LikeCommentRepository likeCommentRepository) {
        this.likeCommentRepository = likeCommentRepository;
    }

    public LikeDTO save(LikeDTO likeDTO) {
        User user = new User(likeDTO.userId());
        Comment comment = new Comment(likeDTO.targetId());
        LikeComment likePost = likeCommentRepository.save(new LikeComment(null, user, comment, LocalDateTime.now()));
        return toLikeDto(likePost.getId(), likeDTO, likePost.getLikeAt());
    }

    public void delete(LikeDTO likeDTO) {
        LikeComment likeComment = likeCommentRepository.findById(likeDTO.id()).orElseThrow();
        likeCommentRepository.delete(likeComment);
    }
}
