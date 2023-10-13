package threads.server.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ReadPostDto {
    private Integer totalPage;
    private Long totalElement;
    private List<PostDto> items;
}