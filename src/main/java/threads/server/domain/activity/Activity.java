package threads.server.domain.activity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    private String content;

    @Column(name = "is_confirmed", nullable = false)
    @ColumnDefault(value = "false")
    private boolean isConfirmed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityStatus status;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Builder
    public Activity(Long toUserId, Long fromUserId, Long targetId, String content, ActivityStatus status) {
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.targetId = targetId;
        this.content = content;
        this.status = status;
        this.issuedAt = LocalDateTime.now();
    }
}
