package threads.server.domain.user;

import org.springframework.stereotype.Service;
import threads.server.application.exception.BadRequestException;
import threads.server.model.User;

import static threads.server.domain.user.UserDTO.toDto;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO signUp(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.email()).ifPresent((user) -> {
            throw new BadRequestException("이미 존재하는 이메일입니다.");
        });

        User user = userRepository.save(new User(null, userDTO.email(), userDTO.name(), userDTO.nickname(), userDTO.userRole()));
        return toDto(user);
    }
}
