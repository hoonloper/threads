package threads.server.domain.like.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.post.Post;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "likes_post")
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column
    private LocalDateTime likeAt;


    private LikePost(CreatingLikeDto likeDto) {
        this.user = new User(likeDto.getUserId());
        this.post = new Post(likeDto.getTargetId());
        this.likeAt = LocalDateTime.now();
    }

    static public LikePost toLikePostEntity(CreatingLikeDto likeDto) {
        return new LikePost(likeDto);
    }
}
