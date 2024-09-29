package com.fastcampus.sns.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcampus.sns.dto.UserDto;
import com.fastcampus.sns.dto.request.UserLoginRequest;
import com.fastcampus.sns.dto.request.UserRegisterRequest;
import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.GlobalControllerAdvice;
import com.fastcampus.sns.exception.SnsAppException;
import com.fastcampus.sns.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(GlobalControllerAdvice.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String apiHeader = "/api/v1/users";


    @Test
    @DisplayName("회원가입 정상 케이스")
    public void test_userRegister() throws Exception {
        String username = "id";
        String password = "pw";
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(username, password);
        when(userService.register(username, password)).thenReturn(mock(UserDto.class));

        mockMvc.perform(post(apiHeader + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRegisterRequest))
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입을 이미 있는 아이디로 시도하면 에러")
    public void test_userRegister_withDuplicateName() throws Exception {
        String username = "id";
        String password = "pw";
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(username, password);
        when(userService.register(username, password)).thenThrow(new SnsAppException(ErrorCode.DUPLICATED_USER_NAME));

        mockMvc.perform(post(apiHeader + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRegisterRequest))
            )
            .andDo(print())
            .andExpect(status().is(ErrorCode.DUPLICATED_USER_NAME.getStatus().value()));
    }

    @Test
    @DisplayName("로그인 정상 케이스")
    public void test_userLogin() throws Exception {
        String username = "hcho302";
        String password = "qwerty";
        UserLoginRequest userLoginRequest = new UserLoginRequest(username, password);
        when(userService.login(username, password)).thenReturn(mock(UserDto.class));

        mockMvc.perform(post(apiHeader + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userLoginRequest))
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인시 가입이 안된 아이디 입력")
    public void test_userLogin_withWrongId() throws Exception {
        String username = "hcho302";
        String password = "qwerty";
        UserLoginRequest userLoginRequest = new UserLoginRequest(username, password);
        when(userService.login(username, password)).thenThrow(new SnsAppException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(post(apiHeader + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userLoginRequest))
            )
            .andDo(print())
            .andExpect(status().is(ErrorCode.USER_NOT_FOUND.getStatus().value()));
    }

    @Test
    @DisplayName("로그인시 비밀번호 틀림")
    public void test_userLogin_withWrongPassword() throws Exception {
        String username = "hcho302";
        String password = "qwerty";
        UserLoginRequest userLoginRequest = new UserLoginRequest(username, password);
        when(userService.login(username, password)).thenThrow(new SnsAppException(ErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post(apiHeader + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userLoginRequest))
            )
            .andDo(print())
            .andExpect(status().is(ErrorCode.INVALID_PASSWORD.getStatus().value()));
    }

}