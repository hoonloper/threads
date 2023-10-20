package threads.server.domain.comment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.comment.dto.CreatingCommentDto;
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

    public Comment(Long id) {
        this.id = id;
    }

    private Comment(final CreatingCommentDto commentDto) {
        this.user = new User(commentDto.getUserId());
        this.post = new Post(commentDto.getPostId());
        this.content = commentDto.getContent();
    }

    static public Comment toComment(final CreatingCommentDto commentDto) {
        return new Comment(commentDto);
    }

    public void change(String content) {
        this.content = content;
    }

    public Boolean checkIfAuthor(Long userId) {
        return user.getId().equals(userId);
    }
}
