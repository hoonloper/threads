package threads.server.domain.post.dto;

import org.springframework.data.domain.Page;
import threads.server.domain.post.Post;
import threads.server.domain.user.dto.UserDto;

import java.util.List;

public class ReadPostDto {
    private final Integer totalPage;
    private final Long totalElement;
    private final List<PostDto> items;
    private ReadPostDto(final Page<Post> postPage, final List<PostDto> items) {
        this.totalPage = postPage.getTotalPages();
        this.totalElement = postPage.getTotalElements();
        this.items = toUserDtoInPosts(items);
    }

    static public ReadPostDto toReadPostDto(final Page<Post> postPage, final List<PostDto> items) {
        return new ReadPostDto(postPage, items);
    }

    private List<PostDto> toUserDtoInPosts(final List<PostDto> postDtoList) {
        return postDtoList.stream().peek(post -> post.setUser(UserDto.toDto(post.getUserEntity()))).toList();
    }
}