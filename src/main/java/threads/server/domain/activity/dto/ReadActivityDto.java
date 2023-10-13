package threads.server.domain.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class ReadActivityDto {
    Integer totalPage;
    Long totalElement;
    List<ActivityDto> items;
}
