package threads.server.domain.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import threads.server.domain.common.BaseEntity;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
}
