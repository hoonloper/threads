package threads.server.domain.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import threads.server.domain.activity.Activity;

public interface ActivityRepository  extends JpaRepository<Activity, Long> {
}
