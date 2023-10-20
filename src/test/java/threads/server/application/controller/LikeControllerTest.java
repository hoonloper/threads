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
import threads.server.domain.like.LikeType;
import threads.server.domain.like.dto.CreatingLikeDto;
import threads.server.domain.like.dto.DeletingLikeDto;
import threads.server.domain.like.service.LikeCommentServiceImpl;
import threads.server.domain.like.service.LikePostServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LikeControllerTest {
    @MockBean
    LikePostServiceImpl likePostServiceImpl;

    @MockBean
    LikeCommentServiceImpl likeCommentServiceImpl;

    @Autowired
    MockMvc mvc;

    private final String END_POINT = "/api/v1/likes";

    @Nested
    @DisplayName("성공 케이스")
    class 성공 {
        @Test
        void 댓글_좋아요() throws Exception {
            CreatingLikeDto likeDto = new CreatingLikeDto(1L, 2L, LikeType.COMMENT);
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(likeDto);
            mvc.perform(post(END_POINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isCreated());
        }
        @Test
        void 쓰레드_좋아요() throws Exception {
            CreatingLikeDto likeDto = new CreatingLikeDto(1L, 2L, LikeType.POST);
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(likeDto);
            mvc.perform(post(END_POINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        void 댓글_좋아요_취소() throws Exception {
            DeletingLikeDto likeDto = new DeletingLikeDto(1L, 1L, 2L, LikeType.COMMENT);
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(likeDto);
            mvc.perform(delete(END_POINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isNoContent());
        }
        @Test
        void 쓰레드_좋아요_취소() throws Exception {
            DeletingLikeDto likeDto = new DeletingLikeDto(1L, 1L, 2L, LikeType.POST);
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(likeDto);
            mvc.perform(delete(END_POINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isNoContent());
        }
    }
}
