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
import threads.server.domain.post.PostService;
import threads.server.domain.post.dto.CreatingPostDTO;
import threads.server.domain.post.dto.DeletingPostDTO;
import threads.server.domain.post.dto.PostDTO;
import threads.server.domain.post.dto.UpdatingPostDTO;
import threads.server.domain.user.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostControllerTest {
    @MockBean
    PostService postService;

    @Autowired
    MockMvc mvc;


    private final String END_POINT = "/api/v1/posts";

    @Nested
    @DisplayName("성공 케이스")
    class 성공 {
        @Test
        void 쓰레드_단건_조회() throws Exception {
            LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            User user = new User(1L);
            PostDTO postDto = new PostDTO(1L, user, "쓰레드", new ArrayList<>(), today, today);
            given(postService.findOneById(any())).willReturn(postDto);

            mvc.perform(get(END_POINT + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(postDto.id()))
                    .andExpect(jsonPath("$.content").value(postDto.content()))
                    .andExpect(jsonPath("$.user").isNotEmpty())
                    .andExpect(jsonPath("$.comments").isArray())
                    .andExpect(jsonPath("$.comments").isEmpty())
                    .andExpect(jsonPath("$.createdAt").value(today.toString()))
                    .andExpect(jsonPath("$.lastModifiedAt").value(today.toString()));
        }

        @Test
        void 쓰레드_생성() throws Exception {
            LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            User user = new User(1L);
            PostDTO postDto = new PostDTO(1L, user, "쓰레드", new ArrayList<>(), today, today);
            given(postService.save(any())).willReturn(postDto);

            CreatingPostDTO inputPostDto = new CreatingPostDTO(user.getId(), postDto.content());
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(inputPostDto);

            mvc.perform(post(END_POINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(postDto.id()))
                    .andExpect(jsonPath("$.content").value(inputPostDto.content()))
                    .andExpect(jsonPath("$.user").isNotEmpty())
                    .andExpect(jsonPath("$.comments").isArray())
                    .andExpect(jsonPath("$.comments").isEmpty())
                    .andExpect(jsonPath("$.createdAt").value(today.toString()))
                    .andExpect(jsonPath("$.lastModifiedAt").value(today.toString()));
        }

        @Test
        void 쓰레드_수정() throws Exception {
            LocalDateTime today = LocalDateTime.now();
            User user = new User(1L);
            PostDTO postDto = new PostDTO(1L, user, "쓰레드", new ArrayList<>(), today, today);
            given(postService.update(any())).willReturn(postDto);

            UpdatingPostDTO inputPostDto = new UpdatingPostDTO(postDto.id(), user.getId(), postDto.content());
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(inputPostDto);

            mvc.perform(patch(END_POINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(postDto.id()))
                    .andExpect(jsonPath("$.content").value(inputPostDto.content()))
                    .andExpect(jsonPath("$.user").isNotEmpty())
                    .andExpect(jsonPath("$.comments").isArray())
                    .andExpect(jsonPath("$.comments").isEmpty())
                    .andExpect(jsonPath("$.createdAt").value(today.toString()))
                    .andExpect(jsonPath("$.lastModifiedAt").value(today.toString()));
        }

        @Test
        void 쓰레드_삭제() throws Exception {
            DeletingPostDTO inputPostDto = new DeletingPostDTO(1L, 1L);
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(inputPostDto);

            mvc.perform(delete(END_POINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isNoContent());
        }
    }
}
