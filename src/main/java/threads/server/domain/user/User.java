package threads.server.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import threads.server.domain.common.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
}
