package threads.server.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.domain.like.LikeDTO;
import threads.server.domain.like.entity.LikePost;
import threads.server.domain.like.repository.LikePostRepository;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikePostService implements LikeService {
    private final LikePostRepository likePostRepository;

    public void save(LikeDTO likeDTO) {
        User user = new User(likeDTO.userId());
        Post post = new Post(likeDTO.targetId());
        likePostRepository.save(new LikePost(null, user, post, LocalDateTime.now()));
    }

    public void delete(LikeDTO likeDTO) {
        LikePost likePost = likePostRepository.findById(likeDTO.id()).orElseThrow(() -> new NotFoundException("좋아요 내역을 찾을 수 없습니다."));
        likePostRepository.delete(likePost);
    }
}
