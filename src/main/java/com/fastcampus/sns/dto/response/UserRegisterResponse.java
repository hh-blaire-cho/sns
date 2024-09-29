package com.fastcampus.sns.dto.response;

import com.fastcampus.sns.dto.UserDto;
import com.fastcampus.sns.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserRegisterResponse {

    private Long id;
    private String username; //비밀번호는 제외하고 아이디, 유저네임 정도만 반환

    private UserRole role;

    public static UserRegisterResponse fromDto(UserDto user) {
        return new UserRegisterResponse(user.getId(), user.getUsername(), user.getRole());
    }

}