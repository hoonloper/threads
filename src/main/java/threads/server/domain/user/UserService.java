package threads.server.domain.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO signUp(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.email()).ifPresent(user -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        User user = userRepository.save(new User(null, userDTO.email(), userDTO.name(), userDTO.nickname(), userDTO.userRole()));
        return toDto(user);
    }

    private UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getNickname(),
                user.getUserRole(),
                user.getCreatedAt(),
                user.getLastModifiedAt()
        );
    }
}
