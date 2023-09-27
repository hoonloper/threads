package threads.server.domain.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.post.Post;
import threads.server.domain.reply.Reply;
import threads.server.domain.user.User;
import threads.server.domain.common.BaseTime;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Reply> replies = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String content;

    public Comment(Long id) {
        this.id = id;
    }

    public Comment(Long id, User user, Post post, String content) {
        this.id = id;
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public void change(String content) {
        this.content = content;
    }
}
