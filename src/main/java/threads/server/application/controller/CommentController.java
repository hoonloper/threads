package threads.server.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.comment.dto.CommentDTO;
import threads.server.domain.comment.CommentService;
import threads.server.domain.comment.dto.CreatingCommentDTO;
import threads.server.domain.comment.dto.RemovingCommentDTO;
import threads.server.domain.comment.dto.UpdatingCommentDTO;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @Operation(summary = "댓글 생성", description = "쓰레드에 댓글을 생성합니다.", tags = { "댓글 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = CommentDTO.class))
            ),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(@RequestBody CreatingCommentDTO commentDTO) {
        return commentService.save(commentDTO);
    }

    @Operation(summary = "댓글 수정", description = "쓰레드에 자신의 댓글을 수정합니다.", tags = { "댓글 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = CommentDTO.class))
            ),
    })
    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO updateComment(@RequestBody UpdatingCommentDTO commentDTO) {
        return commentService.update(commentDTO);
    }


    @Operation(summary = "댓글 삭제", description = "쓰레드에 자신의 댓글을 삭제합니다.", tags = { "댓글 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeComment(@RequestBody RemovingCommentDTO commentDto) {
        commentService.delete(commentDto);
    }
}
