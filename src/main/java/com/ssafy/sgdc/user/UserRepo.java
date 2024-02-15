package com.ssafy.sgdc.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, Integer> {

    boolean existsByLoginId(String loginId);

    boolean existsByUserNickname(String userNickname);

    boolean existsByUserSsafyId(int userSsafyId);

    boolean existsByUserPhone(String userPhone);

    Optional<User> findByLoginId(String loginId);

    Optional<User> findByUserId(int userId);

    @Query("SELECT MIN(u.userId) FROM User u")
    int findMinUserId();

    @Query("SELECT MAX(u.userId) FROM User u")
    int findMaxUserId();

    @Modifying
    @Query("UPDATE User u SET u.challengeCnt = 3")
    void updateAllUsersChallengeCount();

    // 유저 닉네임 검색
    Page<User> findByUserNicknameContaining(String keyword, Pageable pageable);

    // 이메일 중복 검사
    boolean existsByUserEmail(String userEmail);

}

