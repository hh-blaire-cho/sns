package com.fastcampus.sns.service;

import com.fastcampus.sns.dto.UserDto;
import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsAppException;
import com.fastcampus.sns.repository.UserRepository;
import com.fastcampus.sns.util.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;


    // 회원가입
    @Transactional // 예외가 발생을 하면 롤백이 됨
    public UserDto register(String username, String password) {
        // 해당 유저네임으로 이미 가입한 이력이 있는지 확인
        userRepository.findByUsername(username).ifPresent(x -> {
            throw new SnsAppException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s already exists", username));
        });
        // 정상 케이스면 레포에 추가
        UserEntity savedUser = userRepository.save(UserEntity.of(username, encoder.encode(password)));
        return UserDto.fromEntity(savedUser); // NOTE: 반환할 때는 무조건 DTO!! (서비스는 DTO로 주고받아야함)
    }

    // TODO JWT (로그인을 했을 때 암호화된 문자열 반환)
    public String login(String username, String password) {
        // 회원가입 여부 확인
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new SnsAppException(ErrorCode.USER_NOT_FOUND, username));

        // 비밀번호 확인
        if (!encoder.matches(password, userEntity.getPassword())) {
            throw new SnsAppException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성 후 반환
        return JwtTokenUtils.generateAccessToken(username, secretKey, expiredTimeMs);
    }

    public UserDto loadUserDtoByUsername(String username) {
        return userRepository.findByUsername(username).map(UserDto::fromEntity)
            .orElseThrow(() ->
                new SnsAppException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", username)));
    }

}