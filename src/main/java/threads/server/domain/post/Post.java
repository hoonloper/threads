package threads.server.domain.post;

import jakarta.persistence.*;
import threads.server.domain.common.BaseEntity;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(columnDefinition = "TEXT")
    private String content;
}
