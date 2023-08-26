package threads.server.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import threads.server.application.exception.BadRequestException;
import threads.server.domain.user.dto.SignInDTO;
import threads.server.domain.user.dto.SignUpDTO;
import threads.server.domain.user.dto.UserDTO;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    private final String EMAIL = "TEST@test.com";
    private final String PASSWORD = "1234";
    private final String NAME = "이름";
    private final String NICKNAME = "닉네임";

    @Nested
    @DisplayName("성공 케이스")
    class 성공 {

        @Test
        @DisplayName("회원가입 테스트")
        void 회원가입() {
            SignUpDTO inputUserDto = new SignUpDTO(EMAIL, PASSWORD, NAME, NICKNAME, UserRole.USER);
            UserDTO outputUserDto = userService.signUp(inputUserDto);

            assertThat(outputUserDto).isNotNull();
            assertThat(outputUserDto.id()).isNotNull();
            assertThat(outputUserDto.email()).isEqualTo(inputUserDto.email());
            assertThat(outputUserDto.name()).isEqualTo(inputUserDto.name());
            assertThat(outputUserDto.nickname()).isEqualTo(inputUserDto.nickname());
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class 실패 {
        @Test
        @DisplayName("회원가입 존재하는 이메일")
        void 회원가입() {
            SignUpDTO inputUserDto = new SignUpDTO(EMAIL, PASSWORD, NAME, NICKNAME, UserRole.USER);
            userService.signUp(inputUserDto);

            String FAIL_MESSAGE = "이미 존재하는 이메일입니다.";
            assertThatThrownBy(() -> userService.signUp(inputUserDto))
                    .isExactlyInstanceOf(BadRequestException.class)
                    .isInstanceOf(Exception.class)
                    .hasMessage(FAIL_MESSAGE);
        }
    }
}