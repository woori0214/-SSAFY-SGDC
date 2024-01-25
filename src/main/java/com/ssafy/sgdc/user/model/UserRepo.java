package com.ssafy.sgdc.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByLoginId(String loginId);
    boolean existsByUserNickname(String userNickname);
//    boolean existsByUserSsafyId(String userSsafyId);
//    boolean existsByUserPhone(String userPhone);

}
