package threads.server.application.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.reply.ReplyService;
import threads.server.domain.reply.dto.CreatingReplyDto;
import threads.server.domain.reply.dto.DeletingReplyDto;
import threads.server.domain.reply.dto.ReplyDto;
import threads.server.domain.reply.dto.UpdatingReplyDto;

@RestController
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @Operation(summary = "댓글의 답글 생성", description = "쓰레드에 댓글을 생성합니다.", tags = { "댓글의 답글 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = ReplyDto.class))
            ),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReplyDto createReply(@RequestBody CreatingReplyDto replyDto) {
        return replyService.save(replyDto);
    }

    @Operation(summary = "댓글의 답글 수정", description = "댓글의 답글을 수정합니다.", tags = { "댓글의 답글 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = ReplyDto.class))
            ),
    })
    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReplyDto updateReply(@RequestBody UpdatingReplyDto replyDto) {
        return replyService.update(replyDto);
    }


    @Operation(summary = "댓글의 답글 삭제", description = "댓글의 답글을 삭제합니다.", tags = { "댓글의 답글 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeReply(@RequestBody DeletingReplyDto replyDto) {
        replyService.delete(replyDto);
    }
}
