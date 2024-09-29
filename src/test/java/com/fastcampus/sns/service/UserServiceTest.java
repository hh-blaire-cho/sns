package com.fastcampus.sns.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.exception.SnsAppException;
import com.fastcampus.sns.fixture.UserEntityFixture;
import com.fastcampus.sns.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userEntityRepository;

    @Test
    @DisplayName("회원가입 정상 케이스")
    void test_userRegister() {
        String username = "id";
        String password = "pw";
        UserEntity fixture = UserEntityFixture.get(username, password);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(fixture);
        Assertions.assertDoesNotThrow(() -> userService.register(username, password));
    }

    @Test
    @DisplayName("회원가입을 이미 있는 아이디로 시도하면 에러")
    void test_userRegister_withDuplicateName() {
        String username = "id";
        String password = "pw";
        UserEntity fixture = UserEntityFixture.get(username, password);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
        Assertions.assertThrows(SnsAppException.class, () -> userService.register(username, password));
    }

}