package com.fastcampus.sns.dto.response;

import com.fastcampus.sns.dto.UserDto;
import com.fastcampus.sns.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserLoginResponse {

    private Long id;
    private String username; //비밀번호는 제외하고 아이디, 유저네임 정도만 반환한다.

    private UserRole role;

    public static UserLoginResponse fromDto(UserDto user) {
        return new UserLoginResponse(user.getId(), user.getUsername(), user.getRole());
    }

}