package threads.server.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.DeletingLikeDto;
import threads.server.domain.like.entity.LikePost;
import threads.server.domain.like.repository.LikePostRepository;

@Service
@RequiredArgsConstructor
public class LikePostServiceImpl implements LikeService {
    private final LikePostRepository likePostRepository;

    public void save(final CreatingLikeDto likeDto) {
        likePostRepository.save(LikePost.toLikePostEntity(likeDto));
    }

    public void delete(final DeletingLikeDto likeDto) {
        LikePost likePost = likePostRepository.findByUserIdAndPostId(likeDto.getUserId(), likeDto.getTargetId()).orElseThrow(() -> new NotFoundException("좋아요 내역을 찾을 수 없습니다."));
        likePostRepository.delete(likePost);
    }
}
