package threads.server.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.activity.ActivityService;
import threads.server.domain.activity.dto.SaveActivityDto;
import threads.server.domain.comment.dto.*;
import threads.server.domain.comment.CommentService;
import threads.server.domain.reply.ReplyService;
import threads.server.domain.reply.dto.ReadReplyDto;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ReplyService replyService;
    private final ActivityService activityService;

    @Operation(summary = "댓글 생성", description = "쓰레드에 댓글을 생성합니다.", tags = { "댓글 API" })
    @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = CommentDto.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@RequestBody @Valid CreatingCommentDto commentDto) {
        CommentDto comment = commentService.save(commentDto);
        activityService.saveActivity(SaveActivityDto.getCreatingCommentActivity(commentDto));
        return comment;
    }

    @Operation(summary = "댓글 수정", description = "쓰레드에 자신의 댓글을 수정합니다.", tags = { "댓글 API" })
    @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = CommentDto.class)))
    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto updateComment(@RequestBody @Valid UpdatingCommentDto commentDto) {
        return commentService.update(commentDto);
    }


    @Operation(summary = "댓글 삭제", description = "쓰레드에 있는 자신의 댓글을 삭제합니다.", tags = { "댓글 API" })
    @ApiResponse(responseCode = "204", description = "NO_CONTENT")
    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeComment(@PathVariable("commentId") Long commentId, @RequestParam("userId") Long userId) {
        commentService.delete(commentId, userId);
    }


    @Operation(summary = "댓글의 답글 조회", description = "댓글에 작성된 답글을 가져옵니다.", tags = { "댓글 API" })
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CommentDto.class)))
    @GetMapping("{commentId}/replies")
    @ResponseStatus(HttpStatus.OK)
    public ReadReplyDto getRepliesByCommentId(Pageable pageable, @PathVariable(value = "commentId") Long commentId, @RequestParam(value = "userId") Long userId) {
        return replyService.findAllByCommentId(pageable, commentId, userId);
    }
}
