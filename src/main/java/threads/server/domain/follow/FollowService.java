package threads.server.domain.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.follow.dto.FollowDTO;
import threads.server.domain.follow.dto.FollowingDTO;
import threads.server.domain.follow.dto.UnfollowingDTO;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    public void follow(FollowingDTO followDto) {
        User toUser = new User(followDto.toUserId());
        User fromUser = new User(followDto.fromUserId());
        followRepository.save(new Follow(null, toUser, fromUser, LocalDateTime.now()));
    }

    public void unfollow(UnfollowingDTO followDto) {
        Follow follow = followRepository.findById(followDto.id()).orElseThrow(() -> new NotFoundException("팔로우 내역을 찾을 수 없습니다."));
        if(!followDto.fromUserId().equals(follow.getFromUser().getId()) ||
            !followDto.toUserId().equals(follow.getToUser().getId())) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        followRepository.delete(follow);
    }
}
