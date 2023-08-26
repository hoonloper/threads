package threads.server.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.follow.dto.FollowDTO;
import threads.server.domain.follow.FollowService;
import threads.server.domain.follow.dto.FollowingDTO;

@RestController
@RequestMapping("/api/v1/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @Operation(summary = "팔로우 하기", description = "유저를 팔로우합니다.", tags = { "팔로우 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void follow(@RequestBody FollowingDTO followDTO) {
        followService.follow(followDTO);
    }


    @Operation(summary = "팔로우 끊기", description = "유저 팔로우를 끊습니다.", tags = { "팔로우 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
    })
    @DeleteMapping("{followId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollow(@PathVariable("followId") Long followId) {
        followService.unfollow(followId);
    }
}
