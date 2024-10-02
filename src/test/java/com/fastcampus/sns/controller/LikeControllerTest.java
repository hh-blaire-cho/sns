package com.fastcampus.sns.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsAppException;
import com.fastcampus.sns.service.LikeService;
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

@DisplayName("뷰 컨트롤러 - 좋아요")
@AutoConfigureMockMvc
@SpringBootTest
class LikeControllerTest {

    private final String apiHeader = "/api/v1/posts/{postId}/likes";

    @MockBean
    private LikeService likeService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "testUser", roles = {"USER"}) // Mock 사용자 추가
    @DisplayName("게시글 좋아요 정상 케이스")
    public void test_likePost() throws Exception {
        // given
        Long postId = 1L;

        mockMvc.perform(post(apiHeader, postId)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "testUser", roles = {"USER"}) // Mock 사용자 추가
    @DisplayName("게시글 좋아요를 자기글에 하려하면 에러")
    public void test_likePost_selfLike() throws Exception {
        // given
        Long postId = 1L;
        doThrow(new SnsAppException(ErrorCode.SELF_LIKE_NOT_ALLOWED))
            .when(likeService).toggleLikePost(eq(postId), eq("testUser"));

        mockMvc.perform(post(apiHeader, postId)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(ErrorCode.SELF_LIKE_NOT_ALLOWED.getStatus().value()));
    }


    @Test
    @WithAnonymousUser // 로그인하지 않은 경우를 말함
    @DisplayName("게시글 좋아요를 로그인 없이 하려하면 에러")
    public void test_likePost_withoutLogin() throws Exception {
        // given
        Long postId = 1L;

        mockMvc.perform(post(apiHeader, postId)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(username = "testUser", roles = {"USER"}) // Mock 사용자 추가
    @DisplayName("게시글 좋아요 시 게시글이 없으면 에러")
    void test_unlikePost_withoutPost() throws Exception {
        // given
        Long postId = 1L;
        doThrow(new SnsAppException(ErrorCode.POST_NOT_FOUND))
            .when(likeService).toggleLikePost(eq(postId), eq("testUser"));

        // when
        mockMvc.perform(post(apiHeader, postId)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().is(ErrorCode.POST_NOT_FOUND.getStatus().value()));
    }

}
