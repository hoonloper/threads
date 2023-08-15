package threads.server.domain.post;

import org.springframework.stereotype.Service;
import threads.server.domain.comment.Comment;
import threads.server.domain.comment.CommentDTO;
import threads.server.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDTO findOneById(Long postId) {
        Optional<Post> foundPost = postRepository.findById(postId);
        if(foundPost.isEmpty()) {
            throw new IllegalStateException("없는 쓰레드입니다.");
        }
        Post post = foundPost.get();
        return toPostDto(post);
    }

    public PostDTO save(PostDTO postDTO) {
        Post post = postRepository.save(new Post(null, new User(postDTO.userId()), postDTO.content()));
        return toPostDto(post);
    }

    private PostDTO toPostDto(Post post) {
        return new PostDTO(
                post.getId(),
                post.getUser().getId(),
                post.getContent(),
                toCommentsDto(post.getComments()),
                post.getCreatedAt(),
                post.getLastModifiedAt()
        );
    }

    private List<CommentDTO> toCommentsDto(List<Comment> comments) {
        return comments
                .stream()
                .map(comment ->
                        new CommentDTO(
                                comment.getId(),
                                comment.getPost().getId(),
                                comment.getUser().getId(),
                                comment.getContent(),
                                comment.getCreatedAt(),
                                comment.getLastModifiedAt()
                        )
                ).collect(Collectors.toList());
    }
}
