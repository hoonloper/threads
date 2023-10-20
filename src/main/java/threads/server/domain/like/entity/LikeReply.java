package threads.server.domain.like.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.reply.Reply;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "likes_reply")
public class LikeReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id", nullable = false)
    private Reply reply;

    @Column
    private LocalDateTime likeAt;

    private LikeReply(CreatingLikeDto likeDto) {
        this.user = new User(likeDto.getUserId());
        this.reply = new Reply(likeDto.getTargetId());
        this.likeAt = LocalDateTime.now();
    }

    static public LikeReply toLikeReplyEntity(CreatingLikeDto likeDto) {
        return new LikeReply(likeDto);
    }
}
