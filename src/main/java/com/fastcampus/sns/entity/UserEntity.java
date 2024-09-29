package com.fastcampus.sns.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;

import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "\"user\"")
@SQLDelete(sql = "UPDATE \"user\" SET deleated = NOW() WHERE id=?")
@Filter(name = "deletedFilter", condition = "deleted_at is NULL")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Column(nullable = false, unique = true, length = 15)
    private String username;

    @Column(nullable = false, length = 20)
    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;


    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static UserEntity of(String username, String password) {
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        return entity;
    }
}