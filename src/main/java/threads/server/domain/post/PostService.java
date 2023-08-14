package threads.server.domain.post;

import org.springframework.stereotype.Service;
import threads.server.domain.user.User;

import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDTO findOneById(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isEmpty()) {
            throw new IllegalStateException("없는 쓰레드입니다.");
        }
        return toDto(post.get());
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
