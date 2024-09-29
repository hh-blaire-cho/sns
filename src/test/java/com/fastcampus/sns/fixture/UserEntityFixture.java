package com.fastcampus.sns.fixture;

import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.entity.UserRole;
import java.sql.Timestamp;
import java.time.Instant;

public class UserEntityFixture {

    public static UserEntity get(String username, String password) {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setRole(UserRole.USER);
        entity.setCreatedAt(Timestamp.from(Instant.now()));
        return entity;
    }
}
