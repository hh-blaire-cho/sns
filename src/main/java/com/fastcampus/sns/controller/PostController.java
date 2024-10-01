package com.fastcampus.sns.controller;

import com.fastcampus.sns.dto.request.PostRequest;
import com.fastcampus.sns.dto.response.ApiResponse;
import com.fastcampus.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<Void> create(@RequestBody PostRequest request, Authentication authentication) {
        String username = authentication.getName();
        postService.createPost(request.getTitle(), request.getContent(), authentication.getName());
        return ApiResponse.success(null);
    }
}
