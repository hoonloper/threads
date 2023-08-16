package threads.server.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.comment.CommentDTO;
import threads.server.domain.comment.CommentService;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(@RequestBody CommentDTO commentDTO) {
        return commentService.save(commentDTO);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO updateComment(@RequestBody CommentDTO commentDTO) {
        return commentService.update(commentDTO);
    }
}
