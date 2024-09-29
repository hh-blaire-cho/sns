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

}