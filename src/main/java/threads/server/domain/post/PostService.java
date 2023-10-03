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
        Page<Post> posts = postRepository.findAllPosts(pageable);
        List<PostDto> postDtoList = posts.stream().map(post -> {
            PostDto postDTO = toPostDto(post);
            postDTO.setCommentCount(commentRepository.countByPostId(post.getId()));
            postDTO.setLikeCount(likePostRepository.countByPostId(post.getId()));
            postDTO.setLiked(likePostRepository.findByUserIdAndPostId(userId, post.getId()).isPresent());
            return postDTO;
        }).toList();
        return new ReadPostDto(posts.getTotalPages(), posts.getTotalElements(), postDtoList);
    }

    public PostDto findOneById(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        PostDto postDTO = toPostDto(post);
        postDTO.setCommentCount(commentRepository.countByPostId(post.getId()));
        postDTO.setLikeCount(likePostRepository.countByPostId(post.getId()));
        postDTO.setLiked(likePostRepository.findByUserIdAndPostId(userId, post.getId()).isPresent());
        return postDTO;
    }

    public PostDto save(CreatingPostDto postDto) {
        Post post = postRepository.save(new Post(null, new User(postDto.userId()), postDto.content()));
        return toPostDto(post);
    }

    public PostDto update(UpdatingPostDto postDto) {
        Post post = postRepository.findById(postDto.id()).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        authorizeUser(postDto.userId(), post.getUser().getId());
        post.change(postDto.content());
        postRepository.save(post);
        return toPostDto(post);
    }


    public void remove(DeletingPostDto postDto) {
        Post post = postRepository.findById(postDto.id()).orElseThrow(() -> new NotFoundException("쓰레드를 찾을 수 없습니다."));
        authorizeUser(postDto.userId(), post.getUser().getId());
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
