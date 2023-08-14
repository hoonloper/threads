package threads.server.domain.post;

import org.springframework.stereotype.Service;
import threads.server.domain.user.User;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDTO save(PostDTO postDTO) {
        Post post = postRepository.save(new Post(null, new User(postDTO.userId()), postDTO.content()));
        return toDto(post);
    }

    private PostDTO toDto(Post post) {
        return new PostDTO(
                post.getId(),
                post.getUser().getId(),
                post.getContent(),
                post.getCreatedAt(),
                post.getLastModifiedAt()
        );
    }
}
