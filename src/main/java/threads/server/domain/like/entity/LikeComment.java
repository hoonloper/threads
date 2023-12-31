package threads.server.domain.like.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.user.User;
import threads.server.domain.comment.Comment;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "likes_comment")
public class LikeComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column
    private LocalDateTime likeAt;

    private LikeComment(CreatingLikeDto likeDto) {
        this.user = new User(likeDto.getUserId());
        this.comment = new Comment(likeDto.getTargetId());
        this.likeAt = LocalDateTime.now();
    }

    static public LikeComment toLikeCommentEntity(CreatingLikeDto likeDto) {
        return new LikeComment(likeDto);
    }
}
