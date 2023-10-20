package threads.server.domain.comment;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.like.entity.LikeComment;
import threads.server.domain.post.Post;
import threads.server.domain.reply.Reply;
import threads.server.domain.user.User;
import threads.server.domain.common.BaseTime;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "comments")
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private final List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private final List<LikeComment> likeComments = new ArrayList<>();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public Comment(Long id, Long userId, Long postId, String content) {
        this.id = id;
        this.user = new User(userId);
        this.post = new Post(postId);
        this.content = content;
    }

    public void change(String content) {
        this.content = content;
    }

    public Boolean checkIfAuthor(Long userId) {
        return user.getId().equals(userId);
    }
}
