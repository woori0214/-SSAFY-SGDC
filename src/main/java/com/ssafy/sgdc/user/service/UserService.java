package com.ssafy.sgdc.user.service;

import com.ssafy.sgdc.user.model.IsAlert;
import com.ssafy.sgdc.user.model.User;
import com.ssafy.sgdc.user.model.dto.UserLoginDto;
import com.ssafy.sgdc.user.model.dto.UserSignUpDto;
import com.ssafy.sgdc.user.model.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User signUp(UserSignUpDto userSignDto){
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
    @Transactional
    public boolean checkId(String loginId){
        boolean loginIdDuplicate = userRepo.existsByLoginId(loginId);
        System.out.println(loginIdDuplicate);
        return loginIdDuplicate;
    }

    @Transactional
    public boolean checkNickname(String userNickname){
        boolean nickNameDuplicate = userRepo.existsByUserNickname(userNickname);
        System.out.println(nickNameDuplicate);
        return nickNameDuplicate;
    }

    @Transactional
    public boolean checkSsafyId(int userSsafyId){
        boolean ssafyIdDuplicate = userRepo.existsByUserSsafyId(userSsafyId);
        System.out.println(ssafyIdDuplicate);
        return ssafyIdDuplicate;
    }
    @Transactional
    public boolean checkPhone(String userPhone){
        boolean userPhoneDuplicate = userRepo.existsByUserPhone(userPhone);
        System.out.println(userPhoneDuplicate);
        return userPhoneDuplicate;
    }

    public User login(UserLoginDto userLoginDto){
        User userLoginId = userRepo.findByLoginId(userLoginDto.getLoginId());

        if(userLoginId==null){
            System.out.println("아이디 못찾음");
            return null;
        }
        else{ //아이디 존재
            if(userLoginId.getUserPassword().equals(userLoginDto.getUserPassword())){
                System.out.println("로그인 성공");
                return userLoginId;

            }
            else{
                System.out.println("아이디와 패스워드가 맞지 않습니다.");
            }

            return null;
        }
    }
}
