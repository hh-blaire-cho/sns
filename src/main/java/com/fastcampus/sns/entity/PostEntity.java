package com.fastcampus.sns.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@Table(name = "post")
@SQLDelete(sql = "UPDATE \"post\" SET deleted_at = NOW() WHERE id=?")
@Filter(name = "deletedFilter", condition = "deleted_at is NULL")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private UserEntity userEntity;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public static PostEntity of(String title, String content, UserEntity userEntity) {
        PostEntity postEntity = new PostEntity();
        postEntity.title = title;
        postEntity.content = content;
        postEntity.userEntity = userEntity;
        return postEntity;
    }

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

}
