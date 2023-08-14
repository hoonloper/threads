package threads.server.domain.like;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import threads.server.domain.common.BaseEntity;

@Entity
@Table(name = "likes")
public class Like extends BaseEntity {
}
