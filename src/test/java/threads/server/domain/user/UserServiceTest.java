package threads.server.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Nested
    @DisplayName("성공 케이스")
    class Success {
        private UserDTO userDto;

        @BeforeEach
        void setup() {
            String EMAIL = "TEST@test.com";
            String PASSWORD = "1234";
            String NAME = "이름";
            String NICKNAME = "닉네임";
            userDto = UserDTO
                    .builder()
                    .email(EMAIL)
                    .password(PASSWORD)
                    .userRole(UserRole.USER)
                    .nickname(NICKNAME)
                    .name(NAME)
                    .build();
        }

        @Test
        @DisplayName("회원가입 테스트")
        void signUp() {
            User user = new User(1L, userDto.email(), userDto.password(), userDto.nickname(), userDto.name(), userDto.userRole());
            when(userRepository.save(any(User.class))).thenReturn(user);

            UserService memberService = new UserService(userRepository);
            System.out.println(userDto.toString());
            UserDTO resultUserDto = memberService.signUp(userDto);

            assertThat(resultUserDto).isNotNull();
            assertThat(resultUserDto.id()).isNotNull().isEqualTo(user.getId());
            assertThat(resultUserDto.email()).isEqualTo(user.getEmail());
            assertThat(resultUserDto.password()).isNull();
            assertThat(resultUserDto.name()).isEqualTo(user.getName());
            assertThat(resultUserDto.nickname()).isEqualTo(user.getNickname());
        }
    }
}