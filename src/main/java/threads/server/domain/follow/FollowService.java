package threads.server.domain.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.domain.follow.dto.FollowDTO;
import threads.server.domain.follow.dto.FollowingDTO;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    public void follow(FollowingDTO followDTO) {
        User toUser = new User(followDTO.toUserId());
        User fromUser = new User(followDTO.fromUserId());
        followRepository.save(new Follow(null, toUser, fromUser, LocalDateTime.now()));
    }

    public void unfollow(Long followId) {
        Follow follow = followRepository.findById(followId).orElseThrow(() -> new NotFoundException("팔로우 내역을 찾을 수 없습니다."));
        followRepository.delete(follow);
    }
}
