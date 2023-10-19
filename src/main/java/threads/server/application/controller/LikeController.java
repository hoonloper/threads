package threads.server.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.activity.ActivityService;
import threads.server.domain.activity.ActivityStatus;
import threads.server.domain.activity.dto.SaveActivityDto;
import threads.server.domain.like.LikeType;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.DeletingLikeDto;
import threads.server.domain.like.service.*;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    private final ActivityService activityService;
    private final LikeService likeService;

    public LikeController(ActivityService activityService, LikeServiceImpl likeService) {
        this.activityService = activityService;
        this.likeService = likeService;
    }

    @Operation(summary = "좋아요 하기", description = "쓰레드 or 댓글 or 답글을 '좋아요'합니다.", tags = { "좋아요 API" })
    @ApiResponse(responseCode = "201", description = "CREATED")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void like(@RequestBody @Valid CreatingLikeDto likeDto) {
        likeService.save(likeDto);
        ActivityStatus status = likeDto.getType().equals(LikeType.POST) ? ActivityStatus.LIKE_POST : likeDto.getType().equals(LikeType.COMMENT) ? ActivityStatus.LIKE_COMMENT : ActivityStatus.LIKE_REPLY;
        SaveActivityDto activityDto = new SaveActivityDto(likeDto.getTargetUserId(), likeDto.getUserId(), likeDto.getTargetId(), null, status);
        activityService.saveActivity(activityDto);
    }

    @Operation(summary = "좋아요 취소", description = "쓰레드 or 댓글 or 답글 좋아요를 취소합니다.", tags = { "좋아요 API" })
    @ApiResponse(responseCode = "204", description = "NO_CONTENT")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLike(@RequestBody @Valid DeletingLikeDto likeDto) {
        likeService.delete(likeDto);
    }
}
