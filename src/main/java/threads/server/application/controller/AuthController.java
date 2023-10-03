package threads.server.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import threads.server.domain.user.dto.SignInDTO;
import threads.server.domain.user.dto.SignUpDTO;
import threads.server.domain.user.dto.UserDto;
import threads.server.domain.user.UserService;

// 인증 관련 API는 Nuxt server에서 관리하는 것으로 변경해서 AuthContoller는 주석처리
//@RestController
//@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @Operation(summary = "회원 가입 요청", description = "회원 정보가 등록됩니다.", tags = { "인증 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
//            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
//            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
//            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto signUp(@Valid @RequestBody SignUpDTO userDTO) {
        return userService.signUp(userDTO);
    }


    @Operation(summary = "로그인 요청", description = "로그인합니다.", tags = { "인증 API" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
    })
    @PostMapping("sign-in")
    public UserDto signIn(@Valid @RequestBody SignInDTO userDTO) {
        return userService.signIn(userDTO);
    }
}
