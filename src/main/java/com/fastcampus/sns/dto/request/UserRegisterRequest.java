package com.fastcampus.sns.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegisterRequest {

    private String username;
    private String password;

}