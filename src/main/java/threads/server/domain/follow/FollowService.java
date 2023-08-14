package threads.server.domain.follow;

import org.springframework.stereotype.Service;
import threads.server.domain.user.User;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public void unfollow(Follow follow) {
        followRepository.delete(follow);
    }

    public Optional<Follow> getFollow(FollowDTO followDTO) {
        return followRepository.findByToUserIdAndFromUserId(followDTO.toUserId(), followDTO.fromUserId());
    }
}
