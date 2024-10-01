package com.fastcampus.sns.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fastcampus.sns.entity.PostEntity;
import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsAppException;
import com.fastcampus.sns.repository.PostRepository;
import com.fastcampus.sns.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
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
    void test_createPost() {
        String title = "randomTitle";
        String content = "randomContent";
        String username = "randomUsername";
        when(userRepo.findByUsername(username)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postRepo.save(any(PostEntity.class))).thenReturn(mock(PostEntity.class));
        Assertions.assertDoesNotThrow(() -> sut.create(title, content, username));

    }

    @Test
    @DisplayName("게시글 작성시 유저가 존재하지 않으면 에러")
    void test_createPost_withoutValidUser() {
        String title = "randomTitle";
        String content = "randomContent";
        String username = "randomUsername";
        when(userRepo.findByUsername(username)).thenReturn(Optional.empty());
        SnsAppException e = assertThrows(SnsAppException.class, () -> sut.create(title, content, username));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());

    }
}
