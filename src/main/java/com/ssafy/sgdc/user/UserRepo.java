package com.ssafy.sgdc.user;

import com.ssafy.sgdc.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByLoginId(String loginId);
    boolean existsByUserNickname(String userNickname);
    boolean existsByUserSsafyId(int userSsafyId);
    boolean existsByUserPhone(String userPhone);
    User findByLoginId(String loginId);
    User findByUserId(long userId);
}
