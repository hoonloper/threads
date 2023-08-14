package threads.server.application.usecase;

import org.springframework.stereotype.Service;
import threads.server.domain.follow.Follow;
import threads.server.domain.follow.FollowDTO;
import threads.server.domain.follow.FollowService;

import java.util.Optional;

@Service
public class FollowUseCase {
    private final FollowService followService;

    public FollowUseCase(FollowService followService) {
        this.followService = followService;
    }

    public void follow(FollowDTO followDTO) {
        Optional<Follow> follow = followService.getFollow(followDTO);

        if (follow.isEmpty()) {
            followService.follow(followDTO);
        } else {
            followService.unfollow(follow.get());
        }
    }
}
