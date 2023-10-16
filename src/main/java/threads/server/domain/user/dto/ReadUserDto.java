package threads.server.domain.user.dto;

import java.util.List;

public record ReadUserDto(
        Integer totalPage,
        Long totalElement,
        List<UserDto> items
) {}
