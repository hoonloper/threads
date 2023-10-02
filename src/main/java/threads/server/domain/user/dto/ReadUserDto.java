package threads.server.domain.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReadUserDto {
    private Integer totalPage;
    private Long totalElement;
    private List<UserDto> items;
}
