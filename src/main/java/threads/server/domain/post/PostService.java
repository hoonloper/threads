package threads.server.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.comment.CommentRepository;
import threads.server.domain.like.repository.LikePostRepository;
import threads.server.domain.post.dto.*;
import threads.server.domain.user.User;

import java.util.List;

import static threads.server.domain.post.dto.PostDTO.toPostDto;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;
    private final LikePostRepository likePostRepository;


    public ReadPostDTO findAllPost(Pageable pageable) {
        Page<Post> posts = postRepository.findAllPosts(pageable);
        List<PostDTO> postDtoList = posts.stream().map(post -> {
            PostDTO postDTO = PostDTO.toPostDto(post);
            postDTO.setCommentCount(commentRepository.countByPostId(post.getId()));
            postDTO.setLikeCount(likePostRepository.countByPostId(post.getId()));
            return postDTO;
        }).toList();
        return new ReadPostDTO(posts.getTotalPages(), posts.getTotalElements(), postDtoList);
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
