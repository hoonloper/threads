package threads.server.application.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.like.LikeDTO;
import threads.server.domain.like.LikeService;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LikeDTO like(@RequestBody LikeDTO likeDTO) {
        return likeService.save(likeDTO);
    }
}
