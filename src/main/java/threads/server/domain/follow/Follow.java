package threads.server.domain.follow;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import threads.server.domain.user.User;

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
    @CreatedDate
    private Long followAt;
}
