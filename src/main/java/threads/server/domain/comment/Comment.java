package threads.server.domain.comment;

import jakarta.persistence.*;
import threads.server.domain.common.BaseEntity;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long postId;

    @Column(columnDefinition = "TEXT")
    private String content;
}
