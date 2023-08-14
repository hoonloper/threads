package threads.server.domain.like;

import jakarta.persistence.*;
import threads.server.domain.common.BaseTime;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

@Entity
@Table(name = "likes")
public class Like extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
