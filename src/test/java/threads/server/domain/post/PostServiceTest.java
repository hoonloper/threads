package threads.server.domain.post;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import threads.server.domain.post.dto.CreatingPostDto;
import threads.server.domain.post.dto.PostDto;
import threads.server.domain.post.dto.DeletingPostDto;
import threads.server.domain.post.dto.UpdatingPostDto;
import threads.server.domain.user.User;
import threads.server.domain.user.UserRepository;
import threads.server.domain.user.UserRole;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class PostServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;

    @Nested
    @DisplayName("성공 케이스")
    class 성공 {
        private User savedUser;
        private Post savedPost;

        @BeforeEach
        void 설정() {
            savedUser = userRepository.save(new User(null, "test@test.com", "비밀번호", "이름", "닉네임", UserRole.USER));
            savedPost = postRepository.save(new Post(null, savedUser, "내용"));
        }

        @Test
        @DisplayName("쓰레드 생성 테스트")
        void 쓰레드_생성() {
            CreatingPostDto inputPostDto = new CreatingPostDto(savedUser.getId(), "쓰레드테스트");
            PostDto outputPostDto = postService.save(inputPostDto);

            assertThat(outputPostDto).isNotNull();
            assertThat(outputPostDto.id()).isNotNull();
            assertThat(outputPostDto.user().id()).isEqualTo(savedUser.getId());
            assertThat(outputPostDto.content()).isEqualTo(inputPostDto.content());
            assertThat(outputPostDto.comments()).isNotNull();
            assertThat(outputPostDto.createdAt()).isInstanceOf(LocalDateTime.class);
            assertThat(outputPostDto.lastModifiedAt()).isInstanceOf(LocalDateTime.class);
        }

        @Test
        @DisplayName("한개 쓰레드 수정 테스트")
        void 한개_쓰레드_수정() {
            UpdatingPostDto inputPostDto = new UpdatingPostDto(savedPost.getId(), savedUser.getId(), "수정한 내용");
            PostDto outputPostDto = postService.update(inputPostDto);

            assertThat(outputPostDto).isNotNull();
            assertThat(outputPostDto.id()).isNotNull(); // AUTO_INCREMENT로 인해 Not Null만 판단
            assertThat(outputPostDto.user().id()).isNotNull();
            assertThat(outputPostDto.content()).isEqualTo(inputPostDto.content());
            assertThat(outputPostDto.comments()).isNotNull();
            assertThat(outputPostDto.createdAt()).isInstanceOf(LocalDateTime.class);
            assertThat(outputPostDto.lastModifiedAt()).isInstanceOf(LocalDateTime.class);
        }

        @Test
        @DisplayName("한개 쓰레드 찾기 테스트")
        void 한개_쓰레드_찾기() {
            PostDto outputPostDto = postService.findOneById(savedPost.getId());

            assertThat(outputPostDto).isNotNull();
            assertThat(outputPostDto.id()).isNotNull();
            assertThat(outputPostDto.user().id()).isNotNull();
            assertThat(outputPostDto.content()).isEqualTo(savedPost.getContent());
            assertThat(outputPostDto.comments()).isNotNull();
            assertThat(outputPostDto.createdAt()).isInstanceOf(LocalDateTime.class);
            assertThat(outputPostDto.lastModifiedAt()).isInstanceOf(LocalDateTime.class);
        }


        @Test
        @DisplayName("한개 쓰레드 삭제 테스트")
        void 한개_쓰레드_삭제() {
            DeletingPostDto inputPostDto = new DeletingPostDto(savedPost.getId(), savedUser.getId());
            postService.remove(inputPostDto);
            assertThat(postRepository.findAll().size()).isEqualTo(0);
        }
    }
}
