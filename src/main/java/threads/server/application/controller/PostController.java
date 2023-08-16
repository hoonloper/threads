package threads.server.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.post.PostDTO;
import threads.server.domain.post.PostService;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("{postId}")
    public PostDTO getOnePost(@PathVariable("postId") Long postId) {
        return postService.findOneById(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        return postService.save(postDTO);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO updatePost(@RequestBody PostDTO postDTO) {
        return postService.update(postDTO);
    }

    @DeleteMapping("{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePost(@PathVariable("postId") Long postId) {
        postService.remove(postId);
    }
}
