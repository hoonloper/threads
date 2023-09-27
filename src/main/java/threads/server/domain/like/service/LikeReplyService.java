package threads.server.domain.like.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.DeletingLikeDto;
import threads.server.domain.like.entity.LikeReply;
import threads.server.domain.like.repository.LikeReplyRepository;
import threads.server.domain.reply.Reply;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeReplyService implements LikeService {
    private final LikeReplyRepository likeReplyRepository;

    public void save(CreatingLikeDto likeDto) {
        User user = new User(likeDto.getUserId());
        Reply reply = new Reply(likeDto.getTargetId());
        likeReplyRepository.save(new LikeReply(null, user, reply, LocalDateTime.now()));
    }

    public void delete(DeletingLikeDto likeDto) {
        LikeReply likePost = likeReplyRepository.findByUserIdAndReplyId(likeDto.getUserId(), likeDto.getTargetId()).orElseThrow(() -> new NotFoundException("좋아요 내역을 찾을 수 없습니다."));
        authorizeUser(likeDto.getUserId(), likePost.getUser().getId());
        likeReplyRepository.delete(likePost);
    }

    private void authorizeUser(Long requestUserId, Long userIdFromLike) {
        if(!requestUserId.equals(userIdFromLike)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }
}
