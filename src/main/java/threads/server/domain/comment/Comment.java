package threads.server.domain.comment;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import threads.server.domain.common.BaseEntity;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
}
