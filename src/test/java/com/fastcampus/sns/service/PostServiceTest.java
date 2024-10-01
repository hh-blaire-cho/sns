package com.fastcampus.sns.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fastcampus.sns.entity.PostEntity;
import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsAppException;
import com.fastcampus.sns.repository.PostRepository;
import com.fastcampus.sns.repository.UserRepository;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@DisplayName("비즈니스 로직 - 게시글")
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService sut;

    @MockBean
    private PostRepository postRepo;

    @MockBean
    private UserRepository userRepo;

    @Test
    @DisplayName("게시글 작성 정상 케이스")
    void test_createPostPost() {
        String title = "randomTitle";
        String content = "randomContent";
        String username = "randomUsername";
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postRepo.save(any(PostEntity.class))).thenReturn(mock(PostEntity.class));
        assertDoesNotThrow(() -> sut.createPost(title, content, username));

    }

    @Test
    @DisplayName("게시글 작성시 유저가 존재하지 않으면 에러")
    void test_createPost_withoutUser() {
        String title = "randomTitle";
        String content = "randomContent";
        String username = "randomUsername";
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());
        SnsAppException e = assertThrows(SnsAppException.class, () -> sut.createPost(title, content, username));
        assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());

    }

    @Test
    @DisplayName("게시글 수정 정상 케이스")
    void test_updatePost() {
        Long postId = 1L;
        String username = "hcho302";
        String title = "updated";
        String content = "updated";
        UserEntity userEntity = new UserEntity();
        PostEntity postEntity = PostEntity.of("title0", "cont0", userEntity);
        when(postRepo.findById(postId)).thenReturn(Optional.of(postEntity));
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(userEntity));
        assertDoesNotThrow(() -> sut.updatePost(postId, title, content, username));
        then(postRepo).should().save(any(PostEntity.class));
    }

    @Test
    @DisplayName("게시글 수정시 게시글이 없으면 에러")
    void test_updatePost_withoutPost() {
        Long postId = 1L;
        String username = "hcho302";
        String title = "updated";
        String content = "updated";
        when(postRepo.findById(postId)).thenReturn(Optional.empty());
        SnsAppException e = assertThrows(SnsAppException.class, () ->
            sut.updatePost(postId, title, content, username));
        assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    @DisplayName("게시글 수정시 유저가 없으면 에러")
    void test_updatePost_withoutUser() {
        Long postId = 1L;
        String username = "hcho302";
        String title = "updated";
        String content = "updated";
        when(postRepo.findById(postId)).thenReturn(Optional.of(mock(PostEntity.class)));
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());
        SnsAppException e = assertThrows(SnsAppException.class, () ->
            sut.updatePost(postId, title, content, username));
        assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    @DisplayName("게시글 수정시 유저와 작성자가 다르면 에러")
    void test_updatePost_userNotEqualToWriter() {
        Long postId = 1L;
        String username = "hcho302";
        String title = "updated";
        String content = "updated";
        UserEntity userEntity1 = new UserEntity();
        UserEntity userEntity2 = new UserEntity();
        PostEntity postEntity = PostEntity.of("title0", "cont0", userEntity1);
        when(postRepo.findById(postId)).thenReturn(Optional.of(postEntity));
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(userEntity2));
        SnsAppException e = assertThrows(SnsAppException.class, () ->
            sut.updatePost(postId, title, content, username));
        assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }

}
