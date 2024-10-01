package com.fastcampus.sns.controller;

import com.fastcampus.sns.dto.request.PostRequest;
import com.fastcampus.sns.dto.response.ApiResponse;
import com.fastcampus.sns.dto.response.PostResponse;
import com.fastcampus.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<Void> create(
        @RequestBody PostRequest request,
        Authentication auth) {
        postService.createPost(request.getTitle(), request.getContent(), auth.getName());
        return ApiResponse.success(null);
    }

    @PutMapping("/{postId}")
    public ApiResponse<PostResponse> update(
        @PathVariable Long postId,
        @RequestBody PostRequest request,
        Authentication auth
    ) {
        postService.updatePost(postId, request.getTitle(), request.getContent(), auth.getName());
        return ApiResponse.success(null);
    }
}
