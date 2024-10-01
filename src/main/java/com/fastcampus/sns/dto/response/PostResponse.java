package com.fastcampus.sns.dto.response;

import com.fastcampus.sns.dto.PostDto;
import com.fastcampus.sns.dto.UserDto;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public
class PostResponse {

    private Long id;
    private String title;
    private String content;
    private UserDto user;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public static PostResponse fromDto(PostDto dto) {
        return new PostResponse(
            dto.getId(),
            dto.getTitle(),
            dto.getContent(),
            dto.getUserDto(),
            dto.getCreatedAt(),
            dto.getUpdatedAt()
        );
    }

}

