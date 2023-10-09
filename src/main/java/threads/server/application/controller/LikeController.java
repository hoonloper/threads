package threads.server.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.application.exception.BadRequestException;
import threads.server.domain.activity.ActivityService;
import threads.server.domain.activity.ActivityStatus;
import threads.server.domain.activity.dto.SaveActivityDto;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.DeletingLikeDto;
import threads.server.domain.like.service.LikeCommentService;
import threads.server.domain.like.service.LikePostService;
import threads.server.domain.like.service.LikeReplyService;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikePostService likePostService;
    private final LikeCommentService likeCommentService;
    private final LikeReplyService likeReplyService;
    private final ActivityService activityService;

    @Operation(summary = "좋아요 하기", description = "쓰레드 or 댓글 or 답글을 '좋아요'합니다.", tags = { "좋아요 API" })
    @ApiResponse(responseCode = "201", description = "CREATED")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void like(@RequestBody @Valid CreatingLikeDto likeDto) {
        SaveActivityDto activityDto = new SaveActivityDto(likeDto.getTargetUserId(), likeDto.getUserId(), likeDto.getTargetId(), null, null);
        switch (likeDto.getType()) {
            case POST -> {
                likePostService.save(likeDto);
                activityDto.setStatus(ActivityStatus.LIKE_POST);
                activityService.saveActivity(activityDto);
            }
            case COMMENT -> {
                likeCommentService.save(likeDto);
                activityDto.setStatus(ActivityStatus.LIKE_COMMENT);
                activityService.saveActivity(activityDto);
            }
            case REPLY -> {
                likeReplyService.save(likeDto);
                activityDto.setStatus(ActivityStatus.LIKE_REPLY);
                activityService.saveActivity(activityDto);
            }
            default -> throw new BadRequestException("잘못된 타입입니다.");
        }
    }

    @Operation(summary = "좋아요 취소", description = "쓰레드 or 댓글 or 답글 좋아요를 취소합니다.", tags = { "좋아요 API" })
    @ApiResponse(responseCode = "204", description = "NO_CONTENT")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLike(@RequestBody @Valid DeletingLikeDto likeDto) {
        switch (likeDto.getType()) {
            case POST -> likePostService.delete(likeDto);
            case COMMENT -> likeCommentService.delete(likeDto);
            case REPLY -> likeReplyService.delete(likeDto);
            default -> throw new BadRequestException("잘못된 타입입니다.");
        }
    }
}
