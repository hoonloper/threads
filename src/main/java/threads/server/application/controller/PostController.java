package threads.server.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.post.PostDTO;
import threads.server.domain.post.PostService;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "쓰레드 단건 조회", description = "ID에 해당하는 쓰레드 정보를 가져옵니다.", tags = { "쓰레드 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))
            ),
    })
    @GetMapping("{postId}")
    public PostDTO getOnePost(@PathVariable("postId") Long postId) {
        return postService.findOneById(postId);
    }

    @Operation(summary = "쓰레드 생성", description = "쓰레드를 생성합니다.", tags = { "쓰레드 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))
            ),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        return postService.save(postDTO);
    }

    @Operation(summary = "쓰레드 수정", description = "쓰레드를 수정합니다.", tags = { "쓰레드 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))
            ),
    })
    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO updatePost(@RequestBody PostDTO postDTO) {
        return postService.update(postDTO);
    }


    @Operation(summary = "쓰레드 삭제", description = "쓰레드를 삭제합니다.", tags = { "쓰레드 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
    })
    @DeleteMapping("{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePost(@PathVariable("postId") Long postId) {
        postService.remove(postId);
    }
}
