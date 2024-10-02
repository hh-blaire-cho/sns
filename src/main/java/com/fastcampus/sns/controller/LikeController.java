package com.fastcampus.sns.controller;

import com.fastcampus.sns.dto.response.ApiResponse;
import com.fastcampus.sns.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ApiResponse<Void> toggleLike(
        @PathVariable Long postId,
        Authentication auth
    ) {
        likeService.toggleLikePost(postId, auth.getName());
        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<Integer> getLikeCount(
        @PathVariable Long postId
    ) {
        int likeCount = likeService.getLikeCount(postId);
        return ApiResponse.success(likeCount);
    }
}

