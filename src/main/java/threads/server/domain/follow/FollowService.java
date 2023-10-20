package threads.server.domain.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.BadRequestException;
import threads.server.application.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    public void follow(final FollowDto followDto) {
        followRepository.findByToUserIdAndFromUserId(followDto.getToUserId(), followDto.getFromUserId()).ifPresent(e -> {
            throw new BadRequestException("이미 팔로우 중 입니다.");
        });
        followRepository.save(Follow.toFollowingEntity(followDto));
    }

    public void unfollow(FollowDto followDto) {
        Follow follow = followRepository.findByToUserIdAndFromUserId(followDto.getToUserId(), followDto.getFromUserId()).orElseThrow(() -> new NotFoundException("팔로우 내역을 찾을 수 없습니다."));
        followRepository.delete(follow);
    }

}
