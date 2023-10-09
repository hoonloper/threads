package threads.server.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import threads.server.domain.comment.CommentService;
import threads.server.domain.comment.dto.CommentDto;
import threads.server.domain.comment.dto.CreatingCommentDto;
import threads.server.domain.comment.dto.DeletingCommentDto;
import threads.server.domain.comment.dto.UpdatingCommentDto;
import threads.server.domain.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CommentControllerTest {
    @MockBean
    CommentService commentService;

    @Autowired
    MockMvc mvc;


    private final String END_POINT = "/api/v1/comments";

    @Nested
    @DisplayName("성공 케이스")
    class 성공 {
        private LocalDateTime today;
        private final UserDto userDto = UserDto.builder().id(1L).build();

        @BeforeEach
        void 설정() {
            today = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        }

        @Test
        void 댓글_생성() throws Exception {
            CommentDto comment = new CommentDto(1L, userDto, 1L, "댓글", today, today);
            given(commentService.save(any())).willReturn(comment);


            CreatingCommentDto commentDto = new CreatingCommentDto(comment.user().id(), comment.postId(), comment.content());
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(commentDto);



            mvc.perform(post(END_POINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(comment.id()))
                    .andExpect(jsonPath("$.user").isNotEmpty())
                    .andExpect(jsonPath("$.postId").value(commentDto.postId()))
                    .andExpect(jsonPath("$.content").value(commentDto.content()))
                    .andExpect(jsonPath("$.createdAt").value(today.toString()))
                    .andExpect(jsonPath("$.lastModifiedAt").value(today.toString()));
        }


        @Test
        void 댓글_수정() throws Exception {
            CommentDto comment = new CommentDto(1L, userDto, 1L, "댓글", today, today);
            given(commentService.update(any())).willReturn(comment);

            UpdatingCommentDto commentDto = new UpdatingCommentDto(comment.id(), comment.user().id(), comment.postId(), comment.content());
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(commentDto);

            mvc.perform(patch(END_POINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(comment.id()))
                    .andExpect(jsonPath("$.user").isNotEmpty())
                    .andExpect(jsonPath("$.postId").value(commentDto.postId()))
                    .andExpect(jsonPath("$.content").value(commentDto.content()))
                    .andExpect(jsonPath("$.createdAt").value(today.toString()))
                    .andExpect(jsonPath("$.lastModifiedAt").value(today.toString()));
        }


        @Test
        void 댓글_삭제() throws Exception {
            DeletingCommentDto commentDto = new DeletingCommentDto(1L, 1L);
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String json = mapper.writeValueAsString(commentDto);

            mvc.perform(delete(END_POINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andDo(print())
                    .andExpect(status().isNoContent());
        }
    }
}
