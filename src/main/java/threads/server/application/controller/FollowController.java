package threads.server.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.application.usecase.FollowUseCase;
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
    @ResponseStatus(HttpStatus.CREATED)
    public void follow(@RequestBody FollowDTO followDTO) {
        followUseCase.follow(followDTO);
    }
}
