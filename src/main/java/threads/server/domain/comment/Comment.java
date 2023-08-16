package threads.server.domain.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.common.BaseTime;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "comments")
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(columnDefinition = "TEXT")
    private String content;

    public void change(String content) {
        this.content = content;
    }
}
