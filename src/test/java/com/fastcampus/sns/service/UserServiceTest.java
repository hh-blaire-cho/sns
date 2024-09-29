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


    @Test
    @DisplayName("로그인 정상 케이스")
    void test_userLogin() {
        String username = "id";
        String password = "pw";
        UserEntity fixture = UserEntityFixture.get(username, password);
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
        Assertions.assertDoesNotThrow(() -> userService.login(username, password));
    }

    @Test
    @DisplayName("로그인시 가입이 안된 아이디 입력")
    void test_userLogin_wrongId() {
        String username = "id";
        String password = "pw";
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
        Assertions.assertThrows(SnsAppException.class, () -> userService.login(username, password));
    }

    @Test
    @DisplayName("로그인시 비밀번호 틀림")
    void test_userLogin_wrongPassword() {
        String username = "id";
        String password = "pw";
        String wrongPassword = "pw...";
        UserEntity fixture = UserEntityFixture.get(username, password);
        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
        Assertions.assertThrows(SnsAppException.class, () -> userService.login(username, wrongPassword));
    }
}