package threads.server.application.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import threads.server.domain.follow.FollowDTO;
import threads.server.domain.follow.FollowService;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowController {
    private final FollowUseCase followUseCase;
    private final FollowService followService;

    public FollowController(FollowUseCase followUseCase, FollowService followService) {
        this.followUseCase = followUseCase;
        this.followService = followService;
    }

    @PostMapping
    public void follow(@RequestBody FollowDTO followDTO) {
        followUseCase.follow(followDTO);
    }
}
