package threads.server.domain.user;

import com.sun.jdi.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import threads.server.application.exception.BadRequestException;
import threads.server.application.exception.NotFoundException;

import java.util.Optional;

import static threads.server.domain.user.UserDTO.toDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO signUp(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.email()).ifPresent((user) -> {
            throw new BadRequestException("이미 존재하는 이메일입니다.");
        });

        User user = userRepository.save(new User(null, userDTO.email(), userDTO.password(), userDTO.name(), userDTO.nickname(), userDTO.userRole()));
        return toDto(user);
    }

    public UserDTO signIn(UserDTO userDTO) {
        String password = userDTO.password();
        User user = userRepository.findOneByEmailAndPassword(userDTO.email(), password).orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
        return toDto(user);
    }
}
