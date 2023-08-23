package threads.server.domain.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import threads.server.domain.comment.Comment;
import threads.server.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import org.hamcrest.Matchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    PostRepository postRepository;

    @Nested
    @DisplayName("성공 케이스")
    class Success {
        private PostDTO postDto;

        @BeforeEach
        void setup() {
            String EMAIL = "TEST@test.com";
            String PASSWORD = "1234";
            String NAME = "이름";
            String NICKNAME = "닉네임";
            postDto = new PostDTO(null, 1L, "쓰레드테스트", null, null, null);
        }
        @Test
        @DisplayName("쓰레드 생성 테스트")
        void 쓰레드생성() {
            User user = new User(postDto.userId());
            Post commentPost = new Post(1L, user, postDto.content());
            Comment comment = new Comment(1L, user, commentPost, "TEST");
            List<Comment> comments = List.of(comment);
            Post post = new Post(1L, user, postDto.content(), comments);

            when(postRepository.save(any(Post.class))).thenReturn(post);

            PostService postService = new PostService(postRepository);
            PostDTO resultPostDto = postService.save(postDto);

            assertThat(resultPostDto).isNotNull();
            assertThat(resultPostDto.id()).isNotNull().isEqualTo(post.getId());
            assertThat(resultPostDto.userId()).isEqualTo(user.getId());
            assertThat(resultPostDto.content()).isEqualTo(post.getContent());
            assertThat(resultPostDto.comments()).isNotNull();
            comments.forEach(c -> {
                assertThat(c.getContent()).isEqualTo(comment.getContent());
                assertThat(c.getPost().hashCode()).isEqualTo(commentPost.hashCode());
                assertThat(c.getId()).isEqualTo(comment.getId());
                assertThat(c.getUser().hashCode()).isEqualTo(user.hashCode());
            });
        }
        @Test
        @DisplayName("한개 쓰레드 찾기 테스트")
        void 한개쓰레드찾기() {
            User user = new User(postDto.userId());
            Post commentPost = new Post(1L, user, postDto.content());
            Comment comment = new Comment(1L, user, commentPost, "TEST");
            List<Comment> comments = List.of(comment);
            Post post = new Post(1L, user, postDto.content(), comments);

            when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

            PostService postService = new PostService(postRepository);
            PostDTO resultPostDto = postService.findOneById(post.getId());

            assertThat(resultPostDto).isNotNull();
            assertThat(resultPostDto.id()).isNotNull().isEqualTo(post.getId());
            assertThat(resultPostDto.userId()).isEqualTo(user.getId());
            assertThat(resultPostDto.content()).isEqualTo(post.getContent());
            assertThat(resultPostDto.comments()).isNotNull();
            comments.forEach(c -> {
                assertThat(c.getContent()).isEqualTo(comment.getContent());
                assertThat(c.getPost().hashCode()).isEqualTo(commentPost.hashCode());
                assertThat(c.getId()).isEqualTo(comment.getId());
                assertThat(c.getUser().hashCode()).isEqualTo(user.hashCode());
            });
        }
    }
}
