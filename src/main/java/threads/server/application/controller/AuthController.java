package threads.server.application.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.user.UserDTO;
import threads.server.domain.user.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO signUp(@RequestBody UserDTO userDTO) {
        return userService.signUp(userDTO);
    }

    @PostMapping("sign-in")
    public UserDTO getMyUserInfo(@RequestBody UserDTO userDTO) {
        return userService.signIn(userDTO);
    }
}
