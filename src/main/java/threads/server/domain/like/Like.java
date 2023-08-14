package threads.server.domain.like;

import jakarta.persistence.*;
import threads.server.domain.common.BaseEntity;

@Entity
@Table(name = "likes")
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long postId;
}
