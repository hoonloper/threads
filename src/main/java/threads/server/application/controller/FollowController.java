package threads.server.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.follow.FollowDTO;
import threads.server.domain.follow.FollowService;

@RestController
@RequestMapping("/api/v1/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void follow(@RequestBody FollowDTO followDTO) {
        followService.follow(followDTO);
    }

    @DeleteMapping("{followId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollow(@PathVariable("followId") Long followId) {
        followService.unfollow(followId);
    }
}
