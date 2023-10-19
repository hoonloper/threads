package threads.server.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.DeletingLikeDto;
import threads.server.domain.like.entity.LikePost;
import threads.server.domain.like.repository.LikePostRepository;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikePostServiceImpl implements LikeService {
    private final LikePostRepository likePostRepository;

    public void save(CreatingLikeDto likeDto) {
        User user = new User(likeDto.getUserId());
        Post post = new Post(likeDto.getTargetId());
        likePostRepository.save(new LikePost(null, user, post, LocalDateTime.now()));
    }

    public void delete(DeletingLikeDto likeDto) {
        LikePost likePost = likePostRepository.findByUserIdAndPostId(likeDto.getUserId(), likeDto.getTargetId()).orElseThrow(() -> new NotFoundException("좋아요 내역을 찾을 수 없습니다."));
        authorizeUser(likeDto.getUserId(), likePost.getUser().getId());
        likePostRepository.delete(likePost);
    }

    private void authorizeUser(Long requestUserId, Long userIdFromLike) {
        if(!requestUserId.equals(userIdFromLike)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }

}
