package threads.server.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.comment.dto.CommentDTO;
import threads.server.domain.post.dto.*;
import threads.server.domain.user.User;

import java.util.List;

import static threads.server.domain.post.dto.PostDTO.toPostDto;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<ReadPostDTO> findAllPost() {
        List<Post> posts = postRepository.findAllPost();
        return posts.stream().map(post -> {
            List<CommentDTO> comments = post.getComments().stream().map(CommentDTO::toCommentDto).toList();
            return new ReadPostDTO(post.getId(), post.getUser(), post.getContent(), comments, post.getCreatedAt(), post.getLastModifiedAt());
        }).toList();
    }

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
