package threads.server.application.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.bulk.*;

@RestController
@RequestMapping("/api/bulk")
@RequiredArgsConstructor
public class BulkController {
    private final PostBulk postBulk;
    private final UserBulk userBulk;
    private final CommentBulk commentBulk;
    private final FollowBulk followBulk;
    private final LikeBulk likeBulk;

    @PostMapping("/{size}")
    @ResponseStatus(HttpStatus.CREATED)
    public void bulkUser(@PathVariable("size") int size) {
        userBulk.bulkInsert(size);
        postBulk.bulkInsert(size);
        commentBulk.bulkInsert(size);
        followBulk.bulkInsert(size - 5);
        likeBulk.bulkInsert(size - 5);
    }
}
