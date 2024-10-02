package com.fastcampus.sns.dto;

import com.fastcampus.sns.entity.LikeEntity;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeDto {

    private Long id;
    private PostDto postDto;
    private UserDto userDto;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static LikeDto fromEntity(LikeEntity entity) {
        return new LikeDto(
            entity.getId(),
            PostDto.fromEntity(entity.getPost()),
            UserDto.fromEntity(entity.getUser()),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
        );
    }
}
