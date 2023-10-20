package threads.server.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.activity.ActivityService;
import threads.server.domain.activity.ActivityStatus;
import threads.server.domain.activity.dto.SaveActivityDto;
import threads.server.domain.follow.FollowService;
import threads.server.domain.follow.dto.FollowingDto;
import threads.server.domain.follow.dto.UnfollowingDto;

@RestController
@RequestMapping("/api/v1/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final ActivityService activityService;

    @Operation(summary = "팔로우 하기", description = "유저를 팔로우합니다.", tags = { "팔로우 API" })
    @ApiResponse(responseCode = "201", description = "CREATED")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void follow(@RequestBody @Valid FollowingDto followDto) {
        followService.follow(followDto);
        activityService.saveActivity(SaveActivityDto.getFollowingActivity(followDto));
    }


    @Operation(summary = "팔로우 끊기", description = "유저 팔로우를 끊습니다.", tags = { "팔로우 API" })
    @ApiResponse(responseCode = "204", description = "NO_CONTENT")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollow(@RequestBody @Valid UnfollowingDto followDto) {
        followService.unfollow(followDto);
    }
}
