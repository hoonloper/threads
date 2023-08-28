package threads.server.application.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.bulk.CommentBulk;
import threads.server.domain.bulk.FollowBulk;
import threads.server.domain.bulk.PostBulk;
import threads.server.domain.bulk.UserBulk;

@RestController
@RequestMapping("/api/v1/bulk")
@RequiredArgsConstructor
public class BulkController {
    private final PostBulk postBulk;
    private final UserBulk userBulk;
    private final CommentBulk commentBulk;
    private final FollowBulk followBulk;

    @PostMapping("/user/{size}")
    @ResponseStatus(HttpStatus.CREATED)
    public void bulkUser(@PathVariable("size") int size) {
        userBulk.bulkInsert(size);
    }

    @PostMapping("/post/{size}")
    @ResponseStatus(HttpStatus.CREATED)
    public void bulkPost(@PathVariable("size") int size) {
        postBulk.bulkInsert(size);
    }

    @PostMapping("/comment/{size}")
    @ResponseStatus(HttpStatus.CREATED)
    public void bulkComment(@PathVariable("size") int size) {
        commentBulk.bulkInsert(size);
    }

    @PostMapping("/follow/{size}")
    @ResponseStatus(HttpStatus.CREATED)
    public void bulkFollow(@PathVariable("size") int size) {
        followBulk.bulkInsert(size);
    }
}
