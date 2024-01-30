package com.ssafy.sgdc.user;

import com.ssafy.sgdc.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByLoginId(String loginId);
    boolean existsByUserNickname(String userNickname);
    boolean existsByUserSsafyId(int userSsafyId);
    boolean existsByUserPhone(String userPhone);
    User findByLoginId(String loginId);
    User findByUserId(long userId);
    @Query("SELECT MIN(u.userId) FROM User u")
    Long findMinUserId();

    @Query("SELECT MAX(u.userId) FROM User u")
    Long findMaxUserId();
}
