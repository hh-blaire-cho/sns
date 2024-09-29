package com.fastcampus.sns.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private String resultCode;
    private T result;

    // NOTE: 성공일 떈 돌려줄 결과가 각기 다양해서 T 다.
    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>("SUCCESS", result);
    }

    // NOTE: 실패할 땐 돌려줄 값이 없어서 Void 다.
    public static ApiResponse<Void> error(String errorCode) {
        return new ApiResponse<>(errorCode, null);
    }
}

