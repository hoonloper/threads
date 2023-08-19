package threads.server.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.comment.CommentDTO;
import threads.server.domain.comment.CommentService;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


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

    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeComment(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }
}
