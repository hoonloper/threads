package threads.server.domain.post;

import org.springframework.stereotype.Service;
import threads.server.application.exceptions.NotFoundException;
import threads.server.domain.user.User;

import static threads.server.domain.post.PostDTO.toPostDto;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDTO findOneById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        return toPostDto(post);
    }

    public PostDTO save(PostDTO postDTO) {
        Post post = postRepository.save(new Post(null, new User(postDTO.userId()), postDTO.content()));
        return toPostDto(post);
    }

    public PostDTO update(PostDTO postDTO) {
        Post post = postRepository.findById(postDTO.id()).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        post.change(postDTO.content());
        postRepository.save(post);
        return toPostDto(post);
    }


    public void remove(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        postRepository.delete(post);
    }
}
