package com.fastcampus.sns.dto;

import com.fastcampus.sns.entity.PostEntity;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {

    private Long id = null;
    private String title;
    private String content;
    private UserDto userDto;
    private Set<LikeDto> likeDtos;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static PostDto fromEntity(PostEntity entity) {
        return new PostDto(
            entity.getId(),
            entity.getTitle(),
            entity.getContent(),
            UserDto.fromEntity(entity.getUserEntity()),
            entity.getLikes().stream()
                .map(LikeDto::fromEntity)
                .collect(Collectors.toCollection(LinkedHashSet::new)),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
        );
    }
}
