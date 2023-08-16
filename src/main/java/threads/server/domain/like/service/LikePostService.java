package threads.server.domain.like.service;

import org.springframework.stereotype.Service;
import threads.server.domain.like.LikeDTO;
import threads.server.domain.like.entity.LikePost;
import threads.server.domain.like.repository.LikePostRepository;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

import static threads.server.domain.like.LikeDTO.toLikeDto;

@Service
public class LikePostService extends LikeService {
    private final LikePostRepository likePostRepository;

    public LikePostService(LikePostRepository likePostRepository) {
        this.likePostRepository = likePostRepository;
    }

    public LikeDTO save(LikeDTO likeDTO) {
        User user = new User(likeDTO.userId());
        Post post = new Post(likeDTO.targetId());
        LikePost likePost = likePostRepository.save(new LikePost(null, user, post, LocalDateTime.now()));
        return toLikeDto(likePost.getId(), likeDTO, likePost.getLikeAt());
    }

    public void delete(LikeDTO likeDTO) {
        LikePost likePost = likePostRepository.findById(likeDTO.id()).orElseThrow();
        likePostRepository.delete(likePost);
    }
}
