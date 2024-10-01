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

    @Transactional
    public void updatePost(Long postId, String title, String content, String username) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
            new SnsAppException(ErrorCode.POST_NOT_FOUND, String.format("cannot find post id of %d", postId)));

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
            new SnsAppException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", username)));

        // TODO
//        if (!Objects.equals(userEntity.getUsername(), username)) {
//            throw new SnsAppException(ErrorCode.INVALID_PERMISSION,
//                String.format("user %s has no permission with post id of %d", username, postId));
//        }

        postEntity.setTitle(title);
        postEntity.setContent(content);

        postRepository.save(postEntity);
    }
}
