package threads.server.domain.user;

import jakarta.persistence.*;
import threads.server.domain.common.BaseEntity;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
