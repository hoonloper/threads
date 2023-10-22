package threads.server.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import threads.server.application.exception.ForbiddenException;
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

    public PostDto findOneById(final Long postId, final Long userId) {
        PostDto postDto = postRepositorySupport.findById(postId, userId).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        postDto.changeUserToUserDto();
        return postDto;
    }

    public PostDto save(final CreatingPostDto postDto) {
        return toPostDto(postRepository.save(Post.toPostEntity(postDto)));
    }

    @Transactional
    public PostDto update(final UpdatingPostDto postDto) {
        Post post = postRepository.findById(postDto.getId()).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        if(!post.checkIfAuthor(postDto.getUserId())) {
            throw new ForbiddenException("권한이 없습니다.");
        }
        post.change(postDto.getContent());
        postRepository.save(post);
        return toPostDto(post);
    }

    @Transactional
    public void remove(final Long postId, final Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        if(!post.checkIfAuthor(userId)) {
            throw new ForbiddenException("권한이 없습니다.");
        }
        postRepository.delete(post);
    }


    public ReadPostDto findAllPost(final Pageable pageable, final Long userId) {
        Page<Post> postPage = postRepositorySupport.findPostPage(pageable, java.util.Optional.empty());
        return ReadPostDto.toReadPostDto(postPage, postRepositorySupport.findAllPosts(pageable, userId));
    }

    public ReadPostDto findAllByUserId(final Pageable pageable, final Long userId) {
        Page<Post> postPage = postRepositorySupport.findPostPage(pageable, Optional.of(userId));
        return ReadPostDto.toReadPostDto(postPage, postRepositorySupport.findAllPostsByUserId(pageable, userId));
    }
}
