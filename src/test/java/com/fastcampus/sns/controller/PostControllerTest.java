package com.fastcampus.sns.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.sns.dto.request.PostRequest;
import com.fastcampus.sns.entity.PostEntity;
import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsAppException;
import com.fastcampus.sns.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@DisplayName("뷰 컨트롤러 - 게시글")
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    private final String apiHeader = "/api/v1/posts";

    @MockBean
    PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"}) // Mock 사용자 추가
    @DisplayName("게시글 작성 정상 케이스")
    public void test_post() throws Exception {
        // given
        String title = "randomTitle";
        String content = "randomContent";
        mockMvc.perform(post(apiHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new PostRequest(title, content)))
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
                .content(objectMapper.writeValueAsBytes(new PostRequest(title, content)))
            )
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"}) // Mock 사용자 추가
    @DisplayName("게시글 수정 정상 케이스")
    void test_updatePost() throws Exception {
        // given
        String title = "randomTitle";
        String content = "randomContent";
        mockMvc.perform(put(apiHeader + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new PostRequest(title, content)))
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"}) // Mock 사용자 추가
    @DisplayName("게시글 수정시 게시글이 없으면 에러")
    void test_updatePost_withoutPost() throws Exception {
        // given
        String title = "randomTitle";
        String content = "randomContent";
        doThrow(new SnsAppException(ErrorCode.POST_NOT_FOUND)).when(postService)
            .updatePost(eq(1L), eq(title), eq(content), eq("testUser"));
        mockMvc.perform(put(apiHeader + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new PostRequest(title, content))))
            .andDo(print())
            .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getStatus().value()));
    }

    @Test
    @WithAnonymousUser // 로그인하지 않은 경우를 말함
    @DisplayName("게시글 수정시 유저가 없으면 에러")
    void test_updatePost_withoutUser() throws Exception {
        // given
        String title = "randomTitle";
        String content = "randomContent";
        mockMvc.perform(put(apiHeader + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new PostRequest(title, content))))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("게시글 수정시 유저와 작성자가 다르면 에러")
    void test_updatePost_userNotEqualToWriter() throws Exception {
        // given
        String title = "randomTitle";
        String content = "randomContent";
        doThrow(new SnsAppException(ErrorCode.INVALID_PERMISSION)).when(postService)
            .updatePost(eq(1L), eq(title), eq(content), any());
        mockMvc.perform(put(apiHeader + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new PostRequest(title, content))))
            .andDo(print())
            .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getStatus().value()));
    }
}
