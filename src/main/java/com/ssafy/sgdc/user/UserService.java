package com.ssafy.sgdc.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User signUp(UserSigUpDto userSignDto){
        User user = User.builder()
                .userId(0)
                .loginId(userSignDto.getLoginId())
                .userSsafyId(userSignDto.getUserSsafyId())
                .userEmail(userSignDto.getUserEmail())
                .userNickname(userSignDto.getUserNickname())
                .userName(userSignDto.getUserName())
                .userPhone(userSignDto.getUserPhone())
                .userPassword(userSignDto.getUserPassword())
                .userImg(null) //보류
                .isAlert(IsAlert.ACTIVE)
                .createAt(LocalDateTime.now())
                .signOut(false)
                .badgeId(0) //보류
                .kakaoPush(userSignDto.getKakaoPush())
                .challengeCnt(3)
                .complainCnt(0).build();
        System.out.println("ssafy_user 확인===>");
        System.out.println(userRepo.save(user));
        return userRepo.save(user);
    }

}
