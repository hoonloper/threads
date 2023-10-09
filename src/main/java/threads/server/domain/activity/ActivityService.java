package threads.server.domain.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.domain.activity.dto.SaveActivityDto;
import threads.server.domain.activity.repository.ActivityRepository;

@RequiredArgsConstructor
@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    public void saveActivity(SaveActivityDto activityDto) {
        Activity activity = Activity.builder()
                .targetId(activityDto.getTargetId())
                .toUserId(activityDto.getToUserId())
                .fromUserId(activityDto.getFromUserId())
                .content(activityDto.getContent())
                .status(activityDto.getStatus())
                .build();
        activityRepository.save(activity);
    }
}
