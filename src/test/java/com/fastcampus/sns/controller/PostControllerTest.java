package com.fastcampus.sns.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.sns.dto.request.PostCreateRequest;
import com.fastcampus.sns.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;


@DisplayName("뷰 컨트롤러 - 게시글")
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    private final String apiHeader = "/api/v1/posts";

    @Test
    @DisplayName("게시글 작성 정상 케이스")
    public void test_post() throws Exception {
        // given
        String title = "randomTitle";
        String content = "randomContent";
        mockMvc.perform(post(apiHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new PostCreateRequest(title, content)))
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser // 로그인하지 않은 경우를 말함
    @DisplayName("게시글 작성을 로그인 없이 하려하면 에러")
    public void test_post_withoutLogin() throws Exception {
        // given
        String title = "randomTitle";
        String content = "randomContent";
        mockMvc.perform(post(apiHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new PostCreateRequest(title, content)))
            )
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }
}
