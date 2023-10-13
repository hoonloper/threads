package threads.server.domain.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import threads.server.application.exception.NotFoundException;
import threads.server.application.exception.UnauthorizedException;
import threads.server.domain.comment.repository.CommentRepository;
import threads.server.domain.like.repository.LikePostRepository;
import threads.server.domain.post.dto.*;
import threads.server.domain.post.repository.PostRepository;
import threads.server.domain.post.repository.PostRepositorySupport;
import threads.server.domain.user.User;
import threads.server.domain.user.dto.UserDto;

import java.util.List;

import static threads.server.domain.post.dto.PostDto.toPostDto;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikePostRepository likePostRepository;
    private final PostRepositorySupport postRepositorySupport;


    public ReadPostDto findAllPost(Pageable pageable, Long userId) {
        Page<Post> postPage = postRepository.findAllPosts(pageable);
        List<PostDto> postDtoList = postPage.stream().map(post -> {
            PostDto postDto = toPostDto(post);
            postDto.setCommentCount(commentRepository.countByPostId(post.getId()));
            postDto.setLikeCount(likePostRepository.countByPostId(post.getId()));
            postDto.setLiked(likePostRepository.findByUserIdAndPostId(userId, post.getId()).isPresent());
            return postDto;
        }).toList();
        return new ReadPostDto(postPage.getTotalPages(), postPage.getTotalElements(), postDtoList);
    }

    public PostDto findOneById(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        PostDto postDto = toPostDto(post);
        postDto.setCommentCount(commentRepository.countByPostId(post.getId()));
        postDto.setLikeCount(likePostRepository.countByPostId(post.getId()));
        postDto.setLiked(likePostRepository.findByUserIdAndPostId(userId, post.getId()).isPresent());
        return postDto;
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

    public ReadPostDto findAllByUserId(Pageable pageable, Long userId) {
        PageImpl<Post> postPage = postRepositorySupport.findPostPageByUserId(pageable, userId);
        List<PostDto> postDtoList = postRepositorySupport.findAllPostsByUserId(pageable, userId).stream().map(post -> {
            post.setUser(UserDto.toDto(post.getUserEntity()));
            post.setLiked(likePostRepository.findByUserIdAndPostId(userId, post.getId()).isPresent());
            return post;
        }).toList();
        return new ReadPostDto(postPage.getTotalPages(), postPage.getTotalElements(), postDtoList);
    }

    private void authorizeUser(Long requestUserId, Long userIdFromPost) {
        if (!requestUserId.equals(userIdFromPost)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }

}
