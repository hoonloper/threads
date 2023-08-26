package threads.server.application.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.application.exception.BadRequestException;
import threads.server.domain.like.LikeDTO;
import threads.server.domain.like.LikeType;
import threads.server.domain.like.service.LikeCommentService;
import threads.server.domain.like.service.LikePostService;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikePostService likePostService;
    private final LikeCommentService likeCommentService;


    @Operation(summary = "좋아요 하기", description = "쓰레드 or 댓글을 '좋아요'합니다.", tags = { "좋아요 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void like(@RequestBody LikeDTO likeDTO) {
        if (likeDTO.type().equals(LikeType.POST)) {
            likePostService.save(likeDTO);
        }
        if (likeDTO.type().equals(LikeType.COMMENT)) {
            likeCommentService.save(likeDTO);
        }
        throw new BadRequestException("잘못된 타입입니다.");
    }

    @Operation(summary = "좋아요 취소", description = "쓰레드 or 댓글 좋아요를 취소합니다.", tags = { "좋아요 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLike(@RequestBody LikeDTO likeDTO) {
        if(likeDTO.type().equals(LikeType.POST)) {
            likePostService.delete(likeDTO);
        }
        if(likeDTO.type().equals(LikeType.COMMENT)) {
            likeCommentService.delete(likeDTO);
        }
        throw new BadRequestException("잘못된 타입입니다.");
    }
}
