package threads.server.domain.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import threads.server.domain.comment.dto.*;
import threads.server.domain.comment.repository.CommentRepository;
import threads.server.domain.post.Post;
import threads.server.domain.post.PostRepository;
import threads.server.domain.user.User;
import threads.server.domain.user.UserRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

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


    private CommentDto inputCommentDto;

    @Nested
    @DisplayName("성공 케이스")
    class 성공 {
        private User savedUser;
        private Post savedPost;
        @BeforeEach
        void 설정() {
            savedUser = userRepository.save(new User(1L));
            savedPost = postRepository.save(new Post(1L));
        }
        @Test
        @DisplayName("댓글 생성 테스트")
        void 댓글_생성() {
            CreatingCommentDTO inputCommentDto = new CreatingCommentDTO(savedUser.getId(), savedPost.getId(), "댓글테스트");
            CommentDto outputCommentDto = commentService.save(inputCommentDto);

            assertThat(outputCommentDto).isNotNull();
            assertThat(outputCommentDto.id()).isNotNull();
            assertThat(outputCommentDto.user().id()).isEqualTo(savedUser.getId());
            assertThat(outputCommentDto.content()).isEqualTo(inputCommentDto.content());
            assertThat(outputCommentDto.postId()).isEqualTo(savedPost.getId());
            assertThat(outputCommentDto.createdAt()).isInstanceOf(LocalDateTime.class);
            assertThat(outputCommentDto.lastModifiedAt()).isInstanceOf(LocalDateTime.class);
        }

        @Test
        @DisplayName("한개 댓글 수정 테스트")
        void 한개_댓글_수정() {
            Comment savedComment = commentRepository.save(new Comment(null, savedUser, savedPost, "수정전 내용"));
            String CONTENT = "수정할 내용";
            UpdatingCommentDTO inputCommentDto = new UpdatingCommentDTO(savedComment.getId(), savedUser.getId(), savedPost.getId(), CONTENT);
            CommentDto outputCommentDto = commentService.update(inputCommentDto);

            assertThat(outputCommentDto).isNotNull();
            assertThat(outputCommentDto.id()).isNotNull(); // AUTO_INCREMENT로 인해 Not Null만 판단
            assertThat(outputCommentDto.user().id()).isNotNull().isEqualTo(inputCommentDto.userId());
            assertThat(outputCommentDto.postId()).isNotNull().isEqualTo(inputCommentDto.postId());
            assertThat(outputCommentDto.content()).isEqualTo(inputCommentDto.content());
            assertThat(outputCommentDto.createdAt()).isInstanceOf(LocalDateTime.class);
            assertThat(outputCommentDto.lastModifiedAt()).isInstanceOf(LocalDateTime.class);
        }


        @Test
        @DisplayName("한개 댓글 삭제 테스트")
        void 한개_댓글_삭제() {
            Comment savedComment = commentRepository.save(new Comment(null, savedUser, savedPost, "임시 댓글"));
            DeletingCommentDTO inputCommentDto = new DeletingCommentDTO(savedComment.getId(), savedComment.getUser().getId());
            commentService.delete(inputCommentDto);
            assertThat(commentRepository.findAll().size()).isNotNull().isEqualTo(0);
        }
    }
}
