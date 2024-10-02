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
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Setter
@Getter
@Entity
@Table(name = "\"like\"")
@SQLDelete(sql = "UPDATE \"like\" SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @ManyToOne(optional = false)
    @JoinColumn(name = "username")
    private UserEntity user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public static LikeEntity of(PostEntity post, UserEntity user) {
        LikeEntity entity = new LikeEntity();
        entity.setPost(post);
        entity.setUser(user);
        return entity;
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
