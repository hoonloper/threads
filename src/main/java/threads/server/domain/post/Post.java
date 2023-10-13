package threads.server.domain.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.like.entity.LikePost;
import threads.server.domain.user.User;
import threads.server.domain.common.BaseTime;
import threads.server.domain.comment.Comment;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "posts")
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<LikePost> likePosts = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Post(Long id) {
        this.id = id;
    }

    public Post(Long id, User user, String content) {
        this.id = id;
        this.user = user;
        this.content = content;
    }

    public void change(String content) {
        this.content = content;
    }
}
