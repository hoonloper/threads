package threads.server.domain.reply;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.comment.Comment;
import threads.server.domain.common.BaseTime;
import threads.server.domain.like.entity.LikeReply;
import threads.server.domain.user.User;

import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @OneToMany(mappedBy = "reply", cascade = CascadeType.REMOVE)
    private final List<LikeReply> likeReplies = new ArrayList<>();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public Reply(Long id) {
        this.id = id;
    }

    public void change(String content) {

        this.content = content;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
