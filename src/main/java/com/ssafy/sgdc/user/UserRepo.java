package com.ssafy.sgdc.user;

import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.user.dto.UserInfoDto;
import com.ssafy.sgdc.user.dto.UserInfoModifyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, Integer> {

    boolean existsByLoginId(String loginId);
    boolean existsByUserNickname(String userNickname);
    boolean existsByUserSsafyId(int userSsafyId);
    boolean existsByUserPhone(String userPhone);
    User findByLoginId(String loginId);
    User findByUserId(int userId);
    @Query("SELECT MIN(u.userId) FROM User u")
    int findMinUserId();

    @Query("SELECT MAX(u.userId) FROM User u")
    int findMaxUserId();

    //회원수정
    User updateByUserId(int userId);
}

