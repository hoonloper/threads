package threads.server.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.post.dto.CreatingPostDTO;
import threads.server.domain.post.dto.PostDTO;
import threads.server.domain.post.dto.DeletingPostDTO;
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
        authorizeUser(postDto.userId(), post.getUser().getId());
        post.change(postDto.content());
        postRepository.save(post);
        return toPostDto(post);
    }


    public void remove(DeletingPostDTO postDto) {
        Post post = postRepository.findById(postDto.id()).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        authorizeUser(postDto.userId(), post.getUser().getId());
        postRepository.delete(post);
    }

    private void authorizeUser(Long requestUserId, Long userIdFromPost) {
        if (!requestUserId.equals(userIdFromPost)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }
}
