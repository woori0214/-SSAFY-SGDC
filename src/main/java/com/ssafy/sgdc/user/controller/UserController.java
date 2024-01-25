package com.ssafy.sgdc.user.controller;

import com.ssafy.sgdc.user.model.User;
import com.ssafy.sgdc.user.model.dto.UserLoginDto;
import com.ssafy.sgdc.user.service.UserService;
import com.ssafy.sgdc.user.model.dto.UserSignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getRoot(){
        System.out.println("user경로입니다.");
        return "Hello User";
    }

    @RequestMapping(value = "/signup/", method = RequestMethod.POST)
    public String signUp(@RequestBody UserSignUpDto userSignUpDto){
        userService.signUp(userSignUpDto);
        return "회원가입완료";
    }

    // 아이디 중복체크
    @RequestMapping(value = "/signup/check-id/{loginId}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> checkId(@PathVariable String loginId){
        Map<String, String> response = new HashMap<>();
        System.out.println("아이디 중복체크");
        boolean result = userService.checkId(loginId);   // 중복시 true, 중복X false

        response.put("result", String.valueOf(!result)); // ID 사용가능 result : true
        return ResponseEntity.ok(response);              // ID 사용불가 result : false

    }

    // 닉네임 중복체크
    @RequestMapping(value = "/signup/check-nick/{userNickname}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> checkNickname(@PathVariable String userNickname){
        Map<String, String> response = new HashMap<>();
        System.out.println("닉네임 중복체크");
        boolean result = userService.checkNickname(userNickname);   // 중복시 true, 중복X false

        response.put("result", String.valueOf(result)); // 닉네임 사용가능 result : true
        return ResponseEntity.ok(response);              // 닉네임 사용불가 result : false

    }

    // 싸피 학번중복체크
    @RequestMapping(value = "/signup/check-student-num/{userSsafyId}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> checkSsafyId(@PathVariable String userSsafyId){
        Map<String, String> response = new HashMap<>();
        System.out.println("학번체크");
        boolean result = userService.checkSsafyId(Integer.parseInt(userSsafyId));

        response.put("result", String.valueOf(!result));
        return ResponseEntity.ok(response);
    }

    // 싸피 핸드폰인증
    @RequestMapping(value = "/signup/check-phone/{userPhone}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> checkPhone(@PathVariable String userPhone){
        Map<String, String> response = new HashMap<>();
        System.out.println("폰 번호 체크");
        boolean result = userService.checkPhone(String.valueOf(userPhone));

        response.put("result", String.valueOf(!result));
        return ResponseEntity.ok(response);
    }

}
