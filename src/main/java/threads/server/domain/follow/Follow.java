package threads.server.domain.follow;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.user.User;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @Column
    private LocalDateTime followAt;

    private Follow(FollowDto followingDto) {
        this.toUser = new User(followingDto.getToUserId());
        this.fromUser = new User(followingDto.getFromUserId());
        this.followAt = LocalDateTime.now();
    }

    static public Follow toFollowingEntity(FollowDto followingDto) {
        return new Follow(followingDto);
    }
}
