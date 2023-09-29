package threads.server.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.BadRequestException;
import threads.server.application.exception.NotFoundException;
import threads.server.domain.user.dto.SignInDTO;
import threads.server.domain.user.dto.SignUpDTO;
import threads.server.domain.user.dto.UserDto;

import static threads.server.domain.user.dto.UserDto.toDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto signUp(SignUpDTO userDTO) {
        userRepository.findByEmail(userDTO.email()).ifPresent((user) -> {
            throw new BadRequestException("이미 존재하는 이메일입니다.");
        });

        User user = userRepository.save(new User(null, userDTO.email(), userDTO.password(), userDTO.name(), userDTO.nickname(), userDTO.userRole(), null, null));
        return toDto(user);
    }

    public UserDto signIn(SignInDTO userDTO) {
        String password = userDTO.password();
        User user = userRepository.findOneByEmailAndPassword(userDTO.email(), password).orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
        return toDto(user);
    }
}
