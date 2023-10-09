package threads.server.domain.activity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ActivityService {
    private final ActivityRepository activityRepository;
}
