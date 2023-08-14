package threads.server.domain.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO signUp(UserDTO userDTO) {
        User user = new User(null, userDTO.email(), userDTO.name(), userDTO.nickname(), userDTO.userRole());
        userRepository.save(user);
        return toDto(user);
    }

    private UserDTO toDto(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getNickname(), user.getUserRole());
    }
}
