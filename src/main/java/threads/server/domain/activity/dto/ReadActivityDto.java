package threads.server.domain.activity.dto;

import java.util.List;

public record ReadActivityDto(
    Integer totalPage,
    Long totalElement,
    List<ActivityDto> items
) {}
