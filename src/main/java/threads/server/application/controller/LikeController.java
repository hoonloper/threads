package threads.server.application.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.application.exceptions.BadRequestException;
import threads.server.domain.like.LikeDTO;
import threads.server.domain.like.LikeType;
import threads.server.domain.like.service.LikeCommentService;
import threads.server.domain.like.service.LikePostService;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    private final LikePostService likePostService;
    private final LikeCommentService likeCommentService;

    @Autowired
    public LikeController(LikePostService likePostService, LikeCommentService likeCommentService) {
        this.likePostService = likePostService;
        this.likeCommentService = likeCommentService;
    }

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
