package threads.server.domain.like;

import org.springframework.stereotype.Service;
import threads.server.domain.comment.Comment;
import threads.server.domain.like.entity.LikeComment;
import threads.server.domain.like.entity.LikePost;
import threads.server.domain.like.repository.LikeCommentRepository;
import threads.server.domain.like.repository.LikePostRepository;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

@Service
public class LikeService {
    private final LikePostRepository likePostRepository;
    private final LikeCommentRepository likeCommentRepository;

    public LikeService(LikePostRepository likePostRepository, LikeCommentRepository likeCommentRepository) {
        this.likePostRepository = likePostRepository;
        this.likeCommentRepository = likeCommentRepository;
    }

    public LikeDTO save(LikeDTO likeDTO) {
        User user = new User(likeDTO.userId());
        if (likeDTO.type().equals(LikeType.POST)) {
            Post post = new Post(likeDTO.targetId());
            LikePost likePost = likePostRepository.save(new LikePost(null, user, post, LocalDateTime.now()));
            return toLikeDto(likePost.getId(), likeDTO, likePost.getLikeAt());
        }
        if (likeDTO.type().equals(LikeType.COMMENT)) {
            Comment comment = new Comment(likeDTO.targetId());
            LikeComment likeComment = likeCommentRepository.save(new LikeComment(null, user, comment, LocalDateTime.now()));
            return toLikeDto(likeComment.getId(), likeDTO, likeComment.getLikeAt());
        }
        throw new IllegalStateException("잘못된 타입입니다.");
    }

    private LikeDTO toLikeDto(Long id, LikeDTO likeDTO, LocalDateTime likeAt) {
        return new LikeDTO(id, likeDTO.userId(), likeDTO.targetId(), likeDTO.type(), likeAt);
    }
}
