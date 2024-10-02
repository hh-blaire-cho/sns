package com.fastcampus.sns.service;

import com.fastcampus.sns.dto.LikeDto;
import com.fastcampus.sns.entity.LikeEntity;
import com.fastcampus.sns.entity.PostEntity;
import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsAppException;
import com.fastcampus.sns.repository.LikeRepository;
import com.fastcampus.sns.repository.PostRepository;
import com.fastcampus.sns.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void toggleLikePost(Long postId, String username) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
            new SnsAppException(ErrorCode.POST_NOT_FOUND, String.format("cannot find post id: %d", postId)));

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
            new SnsAppException(ErrorCode.USER_NOT_FOUND, String.format("cannot find user name: %d", postId)));

        // 자기 글에 좋아요를 추가하려고 할 때 예외 발생
        if (Objects.equals(postEntity.getUserEntity(), userEntity)) {
            throw new SnsAppException(ErrorCode.SELF_LIKE_NOT_ALLOWED, "cannot like own post");
        }

        Optional<LikeEntity> likeEntity = likeRepository.findByUserAndPost(userEntity, postEntity);
        if (likeEntity.isPresent()) {
            likeRepository.delete(likeEntity.get());
        } else {
            likeRepository.save(LikeEntity.of(postEntity, userEntity));
        }
    }


    public int getLikeCount(Long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() ->
            new SnsAppException(ErrorCode.POST_NOT_FOUND, String.format("cannot find post id: %d", postId)));

        return likeRepository.countByPost(postEntity);
    }

    @Transactional(readOnly = true)
    public List<LikeDto> getLikes(Long postId) {
        return likeRepository.findByPost_Id(postId)
            .stream()
            .map(LikeDto::fromEntity)
            .toList();
    }

}
