package com.fastcampus.sns.service;

import com.fastcampus.sns.dto.UserDto;
import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsAppException;
import com.fastcampus.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입
    public UserDto register(String username, String password) {
        // 해당 유저네임으로 이미 가입한 이력이 있는지 확인
        userRepository.findByUsername(username).ifPresent(x -> {
            throw new SnsAppException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s already exists", username));
        });

        UserEntity savedUser = userRepository.save(UserEntity.of(username, password)); // 정상 케이스라면 레포에 추가
        return UserDto.fromEntity(savedUser); // NOTE: 반환할 때는 무조건 DTO!! (서비스는 DTO로 주고받아야함)
    }

    // TODO JWT (로그인을 했을 때 암호화된 문자열 반환)
    public UserDto login(String username, String password) {
        // 회원가입 여부 확인
        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new SnsAppException(ErrorCode.USER_NOT_FOUND, username));

        // 비밀번호 확인
        if (!userEntity.getPassword().equals(password)) {
            throw new SnsAppException(ErrorCode.INVALID_PASSWORD);
        }

        //TODO 토큰을 생성하고 그 토큰을 반환해야지 DTO를 반한하면 보안 이슈임.
        return UserDto.fromEntity(userEntity);
    }
}