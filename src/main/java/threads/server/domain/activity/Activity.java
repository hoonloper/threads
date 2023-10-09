package threads.server.domain.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import threads.server.domain.user.User;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "to_user_id", nullable = false)
    private Long toUserId;

    @Column(name = "from_user_id", nullable = false)
    private Long fromUserId;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(columnDefinition = "TEXT")
    private Long content;

    @Column(name = "is_confirmed", nullable = false)
    @ColumnDefault(value = "false")
    private boolean isConfirmed;

    @Enumerated(EnumType.STRING)
    private ActivityStatus status;

    @CreatedDate
    private LocalDateTime issuedAt;
}
