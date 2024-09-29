package com.fastcampus.sns.controller;

import com.fastcampus.sns.dto.UserDto;
import com.fastcampus.sns.dto.request.UserLoginRequest;
import com.fastcampus.sns.dto.request.UserRegisterRequest;
import com.fastcampus.sns.dto.response.ApiResponse;
import com.fastcampus.sns.dto.response.UserLoginResponse;
import com.fastcampus.sns.dto.response.UserRegisterResponse;
import com.fastcampus.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserRegisterResponse> join(@RequestBody UserRegisterRequest request) {
        UserDto dto = userService.register(request.getUsername(), request.getPassword());
        return ApiResponse.success(UserRegisterResponse.fromDto(dto)); // 성공했을 때의 결과
    }


    @PostMapping("/login")
    public ApiResponse<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        UserDto dto = userService.login(request.getUsername(), request.getPassword());
        return ApiResponse.success(UserLoginResponse.fromDto(dto)); // 성공했을 때의 결과
    }

}
