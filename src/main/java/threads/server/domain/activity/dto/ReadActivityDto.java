package threads.server.domain.activity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class ReadActivityDto {
    Integer totalPage;
    Long totalElement;
    List<ActivityDto> items;
}
