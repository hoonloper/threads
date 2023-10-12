package threads.server.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReadPostDto {
    private Integer totalPage;
    private Long totalElement;
    private List<PostDto> items;
}