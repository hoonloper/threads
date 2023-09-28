package threads.server.domain.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threads.server.domain.like.entity.LikeReply;

import java.util.Optional;

public interface LikeReplyRepository extends JpaRepository<LikeReply, Long>, LikeRepository<LikeReply>  {
    Long countByReplyId(Long replyId);

    Optional<LikeReply> findByUserIdAndReplyId(Long userId, Long replyId);
}
