package threads.server.domain.comment;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import threads.server.domain.post.Post;
import threads.server.domain.post.PostRepository;
import threads.server.domain.user.User;
import threads.server.domain.user.UserRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class CommentServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;


    @Nested
    @DisplayName("성공 케이스")
    class 성공 {
        @Test
        @DisplayName("댓글 생성 테스트")
        void 댓글_생성() {
            User user = new User(1L);
            Post post = new Post(1L);
            userRepository.save(user);
            postRepository.save(post);
            CommentDTO commentDto = new CommentDTO(null, user.getId(), post.getId(), "댓글", null, null);
            CommentDTO outputCommentDto = commentService.save(commentDto);


            assertThat(outputCommentDto).isNotNull();
            assertThat(outputCommentDto.id()).isNotNull();
            assertThat(outputCommentDto.userId()).isEqualTo(user.getId());
            assertThat(outputCommentDto.content()).isEqualTo(commentDto.content());
            assertThat(outputCommentDto.postId()).isEqualTo(post.getId());
            assertThat(outputCommentDto.createdAt()).isInstanceOf(LocalDateTime.class);
            assertThat(outputCommentDto.lastModifiedAt()).isInstanceOf(LocalDateTime.class);
        }
    }
}
