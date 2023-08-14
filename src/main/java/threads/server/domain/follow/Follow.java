package threads.server.domain.follow;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "follows")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long toUserId;
    private Long fromUserid;

    @Column
    @CreatedDate
    private Long followAt;
}
