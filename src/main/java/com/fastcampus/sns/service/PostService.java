package com.fastcampus.sns.service;

import com.fastcampus.sns.entity.PostEntity;
import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsAppException;
import com.fastcampus.sns.repository.PostRepository;
import com.fastcampus.sns.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createPost(String title, String content, String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
            new SnsAppException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", username)));
        PostEntity postEntity = PostEntity.of(title, content, userEntity);
        postRepository.save(postEntity);
    }
}
