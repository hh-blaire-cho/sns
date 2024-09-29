package com.fastcampus.sns.dto;

import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private UserRole role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;


    public static UserDto fromEntity(UserEntity entity) {
        return new UserDto(
            entity.getId(),
            entity.getUsername(),
            entity.getPassword(),
            entity.getNickname(),
            entity.getRole(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
        );
    }

}

