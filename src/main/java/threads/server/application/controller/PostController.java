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
import threads.server.domain.post.dto.*;
import threads.server.domain.post.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    @Operation(summary = "쓰레드 전체 조회", description = "최근 작성된 쓰레드를 가져옵니다.", tags = { "쓰레드 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = List.class))
            ),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ReadPostDTO getAllPost(Pageable pageable) {
        return postService.findAllPost(pageable);
    }

    @Operation(summary = "쓰레드 단건 조회", description = "ID에 해당하는 쓰레드 정보를 가져옵니다.", tags = { "쓰레드 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = PostDTO.class))
            ),
    })
    @GetMapping("{postId}")
    @ResponseStatus(HttpStatus.OK)
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
    public PostDTO createPost(@RequestBody CreatingPostDTO postDTO) {
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
    public PostDTO updatePost(@RequestBody UpdatingPostDTO postDTO) {
        return postService.update(postDTO);
    }


    @Operation(summary = "쓰레드 삭제", description = "쓰레드를 삭제합니다.", tags = { "쓰레드 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePost(@RequestBody DeletingPostDTO postDTO) {
        postService.remove(postDTO);
    }
}
