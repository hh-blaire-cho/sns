package com.fastcampus.sns.dto;

import com.fastcampus.sns.entity.UserEntity;
import com.fastcampus.sns.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements UserDetails {


    private Long id;
    private String username;
    private String password;
    private UserRole role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;


    public static UserDto fromEntity(UserEntity entity) {
        return new UserDto(
            entity.getId(),
            entity.getUsername(),
            entity.getPassword(),
            entity.getRole(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getDeletedAt()
        );
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return deletedAt == null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return deletedAt == null;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return deletedAt == null;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return deletedAt == null;
    }

}
