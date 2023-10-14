package threads.server.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.post.dto.*;
import threads.server.domain.post.repository.PostRepository;
import threads.server.domain.post.repository.PostRepositorySupport;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.util.List;
import java.util.Optional;

import static threads.server.domain.post.dto.PostDto.toPostDto;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostRepositorySupport postRepositorySupport;

    public PostDto findOneById(Long postId, Long userId) {
        PostDto postD = postRepositorySupport.findById(postId, userId).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        postD.setUser(UserDto.toDto(postD.getUserEntity()));
        return postD;
    }

    public PostDto save(CreatingPostDto postDto) {
        Post post = postRepository.save(new Post(null, new User(postDto.getUserId()), postDto.getContent()));
        return toPostDto(post);
    }

    public PostDto update(UpdatingPostDto postDto) {
        Post post = postRepository.findById(postDto.getId()).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        authorizeUser(postDto.getUserId(), post.getUser().getId());
        post.change(postDto.getContent());
        postRepository.save(post);
        return toPostDto(post);
    }


    public void remove(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        authorizeUser(userId, post.getUser().getId());
        postRepository.delete(post);
    }


    public ReadPostDto findAllPost(Pageable pageable, Long userId) {
        Page<Post> postPage = postRepositorySupport.findPostPage(pageable, java.util.Optional.empty());
        return new ReadPostDto(
                postPage.getTotalPages(),
                postPage.getTotalElements(),
                toUserDtoInPosts(postRepositorySupport.findAllPosts(pageable, userId))
        );
    }

    public ReadPostDto findAllByUserId(Pageable pageable, Long userId) {
        PageImpl<Post> postPage = postRepositorySupport.findPostPage(pageable, Optional.of(userId));
        return new ReadPostDto(
                postPage.getTotalPages(),
                postPage.getTotalElements(),
                toUserDtoInPosts(postRepositorySupport.findAllPostsByUserId(pageable, userId))
        );
    }

    private List<PostDto> toUserDtoInPosts(List<PostDto> postDtoList) {
        return postDtoList.stream().peek(post -> post.setUser(UserDto.toDto(post.getUserEntity()))).toList();
    }

    private void authorizeUser(Long requestUserId, Long userIdFromPost) {
        if (!requestUserId.equals(userIdFromPost)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }

}
