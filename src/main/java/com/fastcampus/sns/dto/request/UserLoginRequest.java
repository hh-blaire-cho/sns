package com.fastcampus.sns.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {

    private String username;
    private String password;

    // 회원가입과 완전 똑같다. 하지만 목적이 다르기 때문에 별도 클래스를 만들어 주는 것
}