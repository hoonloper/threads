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
import threads.server.domain.comment.CommentService;
import threads.server.domain.comment.dto.CommentDTO;
import threads.server.domain.comment.dto.CreatingCommentDTO;
import threads.server.domain.comment.dto.UpdatingCommentDTO;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        @Test
        void 댓글_생성() throws Exception {
            LocalDateTime today = LocalDateTime.now();
            CommentDTO comment = new CommentDTO(1L, 1L, 1L, "댓글", today, today);
            given(commentService.save(any())).willReturn(comment);


            CreatingCommentDTO commentDto = new CreatingCommentDTO(comment.userId(), comment.postId(), comment.content());
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
                    .andExpect(jsonPath("$.userId").value(commentDto.userId()))
                    .andExpect(jsonPath("$.postId").value(commentDto.postId()))
                    .andExpect(jsonPath("$.content").value(commentDto.content()))
                    .andExpect(jsonPath("$.createdAt").value(today.toString()))
                    .andExpect(jsonPath("$.lastModifiedAt").value(today.toString()));
        }


        @Test
        void 댓글_수정() throws Exception {
            LocalDateTime today = LocalDateTime.now();
            CommentDTO comment = new CommentDTO(1L, 1L, 1L, "댓글", today, today);
            given(commentService.update(any())).willReturn(comment);

            UpdatingCommentDTO commentDto = new UpdatingCommentDTO(comment.id(), comment.userId(), comment.postId(), comment.content());
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
                    .andExpect(jsonPath("$.userId").value(commentDto.userId()))
                    .andExpect(jsonPath("$.postId").value(commentDto.postId()))
                    .andExpect(jsonPath("$.content").value(commentDto.content()))
                    .andExpect(jsonPath("$.createdAt").value(today.toString()))
                    .andExpect(jsonPath("$.lastModifiedAt").value(today.toString()));
        }
    }
}
