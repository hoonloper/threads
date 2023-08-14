package threads.server.application.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import threads.server.domain.user.UserDTO;
import threads.server.domain.user.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("sign-up")
    public UserDTO signUp(@RequestBody UserDTO userDTO) {
        return userService.signUp(userDTO);
    }
}
