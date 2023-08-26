package threads.server.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.like.dto.CreatingLikeDTO;
import threads.server.domain.like.dto.DeletingLikeDTO;
import threads.server.domain.like.entity.LikePost;
import threads.server.domain.like.repository.LikePostRepository;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikePostService implements LikeService {
    private final LikePostRepository likePostRepository;

    public void save(CreatingLikeDTO likeDto) {
        User user = new User(likeDto.userId());
        Post post = new Post(likeDto.targetId());
        likePostRepository.save(new LikePost(null, user, post, LocalDateTime.now()));
    }

    public void delete(DeletingLikeDTO likeDto) {
        LikePost likePost = likePostRepository.findById(likeDto.id()).orElseThrow(() -> new NotFoundException("좋아요 내역을 찾을 수 없습니다."));
        authorizeUser(likeDto.userId(), likePost.getUser().getId());
        likePostRepository.delete(likePost);
    }

    private void authorizeUser(Long requestUserId, Long userIdFromLike) {
        if(!requestUserId.equals(userIdFromLike)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }

}
