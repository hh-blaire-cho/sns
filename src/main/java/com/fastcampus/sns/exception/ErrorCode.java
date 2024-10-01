package com.fastcampus.sns.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SEVERE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Duplicated username"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "Invalid password");


    private final HttpStatus status;
    private final String message;
}