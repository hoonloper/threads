package threads.server.domain.reply;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.comment.Comment;
import threads.server.domain.common.BaseTime;
import threads.server.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "replies")
public class Reply extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(columnDefinition = "TEXT")
    private String content;

    public Reply(Long id) {
        this.id = id;
    }

    public void change(String content) {

        this.content = content;
    }
}
