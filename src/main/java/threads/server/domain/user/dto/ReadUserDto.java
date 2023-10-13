package threads.server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReadUserDto {
    private Integer totalPage;
    private Long totalElement;
    private List<UserDto> items;
}
