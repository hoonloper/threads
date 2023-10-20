package threads.server.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.DeletingLikeDto;
import threads.server.domain.like.entity.LikeReply;
import threads.server.domain.like.repository.LikeReplyRepository;

@Service
@RequiredArgsConstructor
public class LikeReplyServiceImpl implements LikeService {
    private final LikeReplyRepository likeReplyRepository;

    public void save(final CreatingLikeDto likeDto) {
        likeReplyRepository.save(LikeReply.toLikeReplyEntity(likeDto));
    }

    public void delete(final DeletingLikeDto likeDto) {
        LikeReply likeReply = likeReplyRepository.findByUserIdAndReplyId(likeDto.getUserId(), likeDto.getTargetId()).orElseThrow(() -> new NotFoundException("좋아요 내역을 찾을 수 없습니다."));
        likeReplyRepository.delete(likeReply);
    }
}
