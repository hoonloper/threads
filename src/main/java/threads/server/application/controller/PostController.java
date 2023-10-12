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
import threads.server.domain.comment.CommentService;
import threads.server.domain.comment.dto.CommentDto;
import threads.server.domain.comment.dto.ReadCommentDto;
import threads.server.domain.post.PostService;
import threads.server.domain.post.dto.CreatingPostDto;
import threads.server.domain.post.dto.PostDto;
import threads.server.domain.post.dto.ReadPostDto;
import threads.server.domain.post.dto.UpdatingPostDto;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @Operation(summary = "쓰레드 최신 페이지네이션 조회", description = "쓰레드를 최신순으로 페이지네이션합니다.", tags = { "쓰레드 API" })
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ReadPostDto.class)))
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ReadPostDto getAllPost(Pageable pageable, @RequestParam(value = "userId") Long userId) {
        return postService.findAllPost(pageable, userId);
    }

    @Operation(summary = "쓰레드 단건 조회", description = "ID에 해당하는 쓰레드 정보를 가져옵니다.", tags = { "쓰레드 API" })
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = PostDto.class)))
    @GetMapping("{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto getOnePost(@PathVariable("postId") Long postId, @RequestParam(value = "userId") Long userId) {
        return postService.findOneById(postId, userId);
    }

    @Operation(summary = "쓰레드 생성", description = "쓰레드를 생성합니다.", tags = { "쓰레드 API" })
    @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = PostDto.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto createPost(@RequestBody @Valid CreatingPostDto postDto) {
        return postService.save(postDto);
    }

    @Operation(summary = "쓰레드 수정", description = "쓰레드를 수정합니다.", tags = { "쓰레드 API" })
    @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = PostDto.class)))
    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto updatePost(@RequestBody @Valid UpdatingPostDto postDto) {
        return postService.update(postDto);
    }


    @Operation(summary = "쓰레드 삭제", description = "쓰레드를 삭제합니다.", tags = { "쓰레드 API" })
    @ApiResponse(responseCode = "204", description = "NO_CONTENT")
    @DeleteMapping("{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePost(@PathVariable("postId") Long postId, @RequestParam("userId") Long userId) {
        postService.remove(postId, userId);
    }

    @Operation(summary = "쓰레드의 답글 조회", description = "쓰레드에 작성된 답글을 가져옵니다.", tags = { "쓰레드 API" })
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CommentDto.class)))
    @GetMapping("/{postId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public ReadCommentDto getCommentsByPostId(Pageable pageable, @PathVariable(value = "postId") Long postId, @RequestParam(value = "userId") Long userId) {
        return commentService.findAllByPostId(pageable, postId, userId);
    }
}
