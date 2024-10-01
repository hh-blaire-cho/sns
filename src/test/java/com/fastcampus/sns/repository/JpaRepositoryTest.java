package com.fastcampus.sns.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.fastcampus.sns.entity.PostEntity;
import com.fastcampus.sns.entity.UserEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DisplayName("JPA 연결 테스트")
@DataJpaTest
class JpaRepositoryTest {

    private final PostRepository postRepo;
    private final UserRepository usrRepo;

    public JpaRepositoryTest(@Autowired PostRepository postRepo, @Autowired UserRepository usrRepo) {
        this.postRepo = postRepo;
        this.usrRepo = usrRepo;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // When
        List<PostEntity> posts = postRepo.findAll();
        List<UserEntity> members = usrRepo.findAll();

        // Then data.sql에 준비된 데이터 갯수 세기
        assertThat(members).isNotNull().hasSize(5); // 사용재 5명
        assertThat(posts).isNotNull().hasSize(15); // 게시글 15개
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long memberCount = usrRepo.count();
        long postCount = postRepo.count();
        UserEntity member = UserEntity.of("Tom", "qwerty1234");
        PostEntity post1 = PostEntity.of("new title", "new content", member);
        PostEntity post2 = PostEntity.of("new title", "new content", member);

        // When
        usrRepo.save(member);
        postRepo.save(post1);
        postRepo.save(post2);

        // Then
        assertThat(usrRepo.count()).isEqualTo(memberCount + 1);
        assertThat(postRepo.count()).isEqualTo(postCount + 2);
    }
}
