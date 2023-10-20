package threads.server.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.DeletingLikeDto;
import threads.server.domain.like.entity.LikeComment;
import threads.server.domain.like.repository.LikeCommentRepository;

@Service
@RequiredArgsConstructor
public class LikeCommentServiceImpl implements LikeService {
    private final LikeCommentRepository likeCommentRepository;

    public void save(final CreatingLikeDto likeDto) {
        likeCommentRepository.save(LikeComment.toLikeCommentEntity(likeDto));
    }

    public void delete(final DeletingLikeDto likeDto) {
        LikeComment likeComment = likeCommentRepository.findByUserIdAndCommentId(likeDto.getUserId(), likeDto.getTargetId()).orElseThrow();
        likeCommentRepository.delete(likeComment);
    }
}
