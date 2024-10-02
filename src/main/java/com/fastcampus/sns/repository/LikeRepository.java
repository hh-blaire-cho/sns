package com.fastcampus.sns.repository;

import com.fastcampus.sns.entity.LikeEntity;
import com.fastcampus.sns.entity.PostEntity;
import com.fastcampus.sns.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    List<LikeEntity> findAllByPost(PostEntity postEntity);

    List<LikeEntity> findByPost_Id(Long postId);

    int countByPost(PostEntity postEntity);
}
