package threads.server.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.domain.post.dto.CreatingPostDTO;
import threads.server.domain.post.dto.PostDTO;
import threads.server.domain.post.dto.RemovingPostDTO;
import threads.server.domain.post.dto.UpdatingPostDTO;
import threads.server.domain.user.User;

import static threads.server.domain.post.dto.PostDTO.toPostDto;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostDTO findOneById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        return toPostDto(post);
    }

    public PostDTO save(CreatingPostDTO postDto) {
        Post post = postRepository.save(new Post(null, new User(postDto.userId()), postDto.content()));
        return toPostDto(post);
    }

    public PostDTO update(UpdatingPostDTO postDto) {
        Post post = postRepository.findById(postDto.id()).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        post.change(postDto.content());
        postRepository.save(post);
        return toPostDto(post);
    }


    public void remove(RemovingPostDTO postDto) {
        Post post = postRepository.findById(postDto.id()).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        postRepository.delete(post);
    }
}
