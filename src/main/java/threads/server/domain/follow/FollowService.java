package threads.server.domain.follow;

import org.springframework.stereotype.Service;
import threads.server.application.exceptions.NotFoundException;
import threads.server.model.Follow;
import threads.server.model.User;

import java.time.LocalDateTime;

@Service
public class FollowService {
    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public void follow(FollowDTO followDTO) {
        User toUser = new User(followDTO.toUserId());
        User fromUser = new User(followDTO.fromUserId());
        followRepository.save(new Follow(null, toUser, fromUser, LocalDateTime.now()));
    }

    public void unfollow(Long followId) {
        Follow follow = followRepository.findById(followId).orElseThrow(() -> new NotFoundException("팔로우 내역을 찾을 수 없습니다."));
        followRepository.delete(follow);
    }
}
