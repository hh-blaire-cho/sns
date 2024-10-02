package com.fastcampus.sns.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcampus.sns.entity.LikeEntity;
import com.fastcampus.sns.entity.PostEntity;
import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsAppException;
import com.fastcampus.sns.repository.LikeRepository;
import com.fastcampus.sns.repository.PostRepository;
import com.fastcampus.sns.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("비즈니스 로직 - 좋아요")
@SpringBootTest
class LikeServiceTest {

    @Autowired
    LikeService sut;

    @MockBean
    private PostRepository postRepo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private LikeRepository likeRepo;

    @Test
    @DisplayName("좋아요 추가 정상 케이스")
    void test_toggleLikePost_addLike() {
        Long postId = 1L;
        String username = "randomUsername";
        PostEntity postEntity = new PostEntity();
        UserEntity userEntity = new UserEntity();

        when(postRepo.findById(postId)).thenReturn(Optional.of(postEntity));
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(likeRepo.findByUserAndPost(userEntity, postEntity)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> sut.toggleLikePost(postId, username));
        verify(likeRepo, times(1)).save(any(LikeEntity.class));
    }

    @Test
    @DisplayName("좋아요 취소 정상 케이스")
    void test_toggleLikePost_removeLike() {
        Long postId = 1L;
        String username = "randomUsername";
        PostEntity postEntity = new PostEntity();
        UserEntity userEntity = new UserEntity();
        LikeEntity likeEntity = LikeEntity.of(postEntity, userEntity);

        when(postRepo.findById(postId)).thenReturn(Optional.of(postEntity));
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(likeRepo.findByUserAndPost(userEntity, postEntity)).thenReturn(Optional.of(likeEntity));

        assertDoesNotThrow(() -> sut.toggleLikePost(postId, username));
        verify(likeRepo, times(1)).delete(likeEntity);
    }

    @Test
    @DisplayName("좋아요 개수 조회 정상 케이스")
    void test_getLikeCount() {
        Long postId = 1L;
        PostEntity postEntity = new PostEntity();

        when(postRepo.findById(postId)).thenReturn(Optional.of(postEntity));
        when(likeRepo.countByPost(postEntity)).thenReturn(3); // 예를 들어 좋아요가 3개라면

        int likeCount = sut.getLikeCount(postId);
        assertEquals(3, likeCount);
    }

    @Test
    @DisplayName("좋아요 추가 시 게시글이 없으면 에러")
    void test_toggleLikePost_withoutPost() {
        Long postId = 1L;
        String username = "randomUsername";

        when(postRepo.findById(postId)).thenReturn(Optional.empty());

        SnsAppException e = assertThrows(SnsAppException.class, () -> sut.toggleLikePost(postId, username));
        assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    @DisplayName("좋아요 추가 시 유저가 없으면 에러")
    void test_toggleLikePost_withoutUser() {
        Long postId = 1L;
        String username = "randomUsername";
        when(postRepo.findById(postId)).thenReturn(Optional.of(new PostEntity()));
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

        SnsAppException e = assertThrows(SnsAppException.class, () -> sut.toggleLikePost(postId, username));
        assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    @DisplayName("자기 글에 좋아요를 추가하려고 하면 에러")
    void test_toggleLikePost_selfLike() {
        Long postId = 1L;
        String username = "randomUsername";

        UserEntity userEntity = UserEntity.of(username, "pw");
        PostEntity postEntity = PostEntity.of("a", "b", userEntity);  // 포스트 작성자를 현재 사용자로 설정

        when(postRepo.findById(postId)).thenReturn(Optional.of(postEntity));
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(userEntity));

        SnsAppException e = assertThrows(SnsAppException.class, () ->
            sut.toggleLikePost(postId, username));

        assertEquals(ErrorCode.SELF_LIKE_NOT_ALLOWED, e.getErrorCode());
    }

    @Test
    @DisplayName("좋아요 개수 조회 시 게시글이 없으면 에러")
    void test_getLikeCount_withoutPost() {
        Long postId = 1L;

        when(postRepo.findById(postId)).thenReturn(Optional.empty());

        SnsAppException e = assertThrows(SnsAppException.class, () -> sut.getLikeCount(postId));
        assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }
}