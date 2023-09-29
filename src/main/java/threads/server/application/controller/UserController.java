package threads.server.application.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.comment.CommentService;
import threads.server.domain.comment.dto.ReadCommentDto;
import threads.server.domain.post.PostService;
import threads.server.domain.post.dto.ReadPostDto;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final PostService postService;
    private final CommentService commentService;

    @Operation(summary = "유저의 스레드 조회", description = "유저가 작성한 스레드를 가져옵니다.", tags = { "유저 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ReadPostDto.class))
            ),
    })

    @GetMapping("/{userId}/posts")
    @ResponseStatus(HttpStatus.OK)
    public ReadPostDto getPostsByUserId(Pageable pageable, @PathVariable(value = "userId") Long userId) {
        return postService.findAllByUserId(pageable, userId);
    }
    @Operation(summary = "유저의 답글 조회", description = "유저가 작성한 답글을 가져옵니다.", tags = { "유저 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = ReadCommentDto.class))
            ),
    })
    @GetMapping("/{userId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public ReadCommentDto getCommentsByUserId(Pageable pageable, @PathVariable(value = "userId") Long userId) {
        return commentService.findAllByUserId(pageable, userId);
    }
}
