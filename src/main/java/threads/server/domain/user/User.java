package threads.server.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import threads.server.domain.common.BaseTime;
import threads.server.domain.follow.Follow;
import threads.server.domain.like.entity.LikePost;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    @ColumnDefault(value = "false")
    private boolean isHidden;


    @OneToMany(mappedBy = "toUser", cascade = CascadeType.REMOVE)
    private List<Follow> toFollows = new ArrayList<>();


    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.REMOVE)
    private List<Follow> fromFollows = new ArrayList<>();

    public User(Long id) {
        this.id = id;
    }
}
