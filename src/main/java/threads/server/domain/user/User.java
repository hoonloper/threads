package threads.server.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import threads.server.domain.common.BaseTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column
    private String link;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    public User(Long id) {
        this.id = id;
    }
}
