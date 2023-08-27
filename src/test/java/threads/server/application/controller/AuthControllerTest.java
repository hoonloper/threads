package threads.server.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import threads.server.domain.user.UserRole;
import threads.server.domain.user.UserService;
import threads.server.domain.user.dto.SignUpDTO;
import threads.server.domain.user.dto.UserDTO;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTest {
    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

    @Nested
    @DisplayName("성공 케이스")
    class 성공 {
        @Test
        void 회원가입() throws Exception {
            SignUpDTO signUpDto = new SignUpDTO("test@test.com", "A!@$!B@F#BRa1", "이름", "닉네임", UserRole.USER);
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(signUpDto);
            LocalDateTime today = LocalDateTime.now();
            UserDTO returnValue = new UserDTO(1L, signUpDto.email(), signUpDto.name(), signUpDto.nickname(), signUpDto.userRole(), today, today);
            given(userService.signUp(any())).willReturn(returnValue);

            mvc.perform(post("/api/v1/auth/sign-up")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.email").value(signUpDto.email()))
                    .andExpect(jsonPath("$.name").value(signUpDto.name()))
                    .andExpect(jsonPath("$.nickname").value(signUpDto.nickname()))
                    .andExpect(jsonPath("$.userRole").value(signUpDto.userRole().name()))
                    .andExpect(jsonPath("$.createdAt").value(today.toString()))
                    .andExpect(jsonPath("$.lastModifiedAt").value(today.toString()));
        }
    }
}
