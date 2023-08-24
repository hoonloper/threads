package threads.server.domain.post;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import threads.server.domain.user.User;
import threads.server.domain.user.UserRepository;
import threads.server.domain.user.UserRole;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
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
    class Success {
        private final User user = userRepository.save(new User(null, "test@test.com", "비밀번호", "이름", "닉네임", UserRole.USER));;
        private final PostDTO POST_DTO = new PostDTO(null, 1L, "쓰레드테스트", null, null, null);

        private PostDTO savedPostDto;
        @BeforeEach
        void 설정() {
            savedPostDto = postService.save(POST_DTO);
        }

        @Test
        @DisplayName("쓰레드 생성 테스트")
        void 쓰레드_생성() {
            PostDTO inputPostDto = POST_DTO;
            PostDTO outputPostDto = postService.save(inputPostDto);

            assertThat(outputPostDto).isNotNull();
            assertThat(outputPostDto.id()).isNotNull();
            assertThat(outputPostDto.userId()).isEqualTo(user.getId());
            assertThat(outputPostDto.content()).isEqualTo(inputPostDto.content());
            assertThat(outputPostDto.comments()).isNotNull();
            assertThat(outputPostDto.createdAt()).isInstanceOf(LocalDateTime.class);
            assertThat(outputPostDto.lastModifiedAt()).isInstanceOf(LocalDateTime.class);
        }

        @Test
        @DisplayName("한개 쓰레드 수정 테스트")
        void 한개_쓰레드_수정() {
            PostDTO inputPostDto = new PostDTO(savedPostDto.id(), user.getId(), "수정한 내용", null, null, null);
            PostDTO outputPostDto = postService.update(inputPostDto);

            assertThat(outputPostDto).isNotNull();
            assertThat(outputPostDto.id()).isNotNull(); // AUTO_INCREMENT로 인해 Not Null만 판단
            assertThat(outputPostDto.userId()).isNotNull();
            assertThat(outputPostDto.content()).isEqualTo(inputPostDto.content());
            assertThat(outputPostDto.comments()).isNotNull();
            assertThat(outputPostDto.createdAt()).isInstanceOf(LocalDateTime.class);
            assertThat(outputPostDto.lastModifiedAt()).isInstanceOf(LocalDateTime.class);
        }

        @Test
        @DisplayName("한개 쓰레드 찾기 테스트")
        void 한개_쓰레드_찾기() {
            PostDTO outputPostDto = postService.findOneById(savedPostDto.id());

            assertThat(outputPostDto).isNotNull();
            assertThat(outputPostDto.id()).isNotNull();
            assertThat(outputPostDto.userId()).isNotNull();
            assertThat(outputPostDto.content()).isEqualTo(POST_DTO.content());
            assertThat(outputPostDto.comments()).isNotNull();
            assertThat(outputPostDto.createdAt()).isInstanceOf(LocalDateTime.class);
            assertThat(outputPostDto.lastModifiedAt()).isInstanceOf(LocalDateTime.class);
        }


        @Test
        @DisplayName("한개 쓰레드 삭제 테스트")
        void 한개쓰레드삭제() {
            postService.remove(savedPostDto.id());
            List<Post> posts = postRepository.findAll();
            assertThat(posts.size()).isEqualTo(0);
        }
    }
}
