package threads.server.application.controller;

import org.springframework.web.bind.annotation.*;
import threads.server.domain.post.PostDTO;
import threads.server.domain.post.PostService;

@RestController
@RequestMapping("/api/v1/post")
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
    public PostDTO createPost(@RequestBody PostDTO postDTO) {
        return postService.save(postDTO);
    }
}
