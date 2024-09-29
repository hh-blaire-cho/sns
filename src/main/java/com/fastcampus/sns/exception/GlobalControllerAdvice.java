package com.fastcampus.sns.exception;

import com.fastcampus.sns.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(SnsAppException.class)
    public ResponseEntity<?> applicationHandler(SnsAppException e) {
        log.error("Error occurs {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(ApiResponse.error(e.getErrorCode().name()));
    }
}
