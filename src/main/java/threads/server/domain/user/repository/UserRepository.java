package threads.server.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threads.server.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findOneByEmailAndPassword(String email, String password);
}
