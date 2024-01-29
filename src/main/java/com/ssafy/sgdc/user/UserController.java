package com.ssafy.sgdc.user;

import com.ssafy.sgdc.user.dto.UserInfoDto;
import com.ssafy.sgdc.user.dto.UserLoginDto;
import com.ssafy.sgdc.user.dto.UserSignUpDto;
import com.ssafy.sgdc.util.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
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
    public ResponseEntity<GeneralResponse> signUp(@RequestBody UserSignUpDto userSignUpDto){
        System.out.println("회원가입 경로.");
        userService.signUp(userSignUpDto);

        Map<String, String> result = new HashMap<>();
        result.put("result", "true");

        return new ResponseEntity<> (GeneralResponse.builder()
                .status(200)
                .message("회원가입")
                .data(result)
                .build(), HttpStatus.OK);
    }

    // 아이디 중복체크
    @RequestMapping(value = "/signup/check-id/{loginId}", method = RequestMethod.GET)
    public ResponseEntity<GeneralResponse> checkId(@PathVariable String loginId){
        Map<String, String> response = new HashMap<>();
        System.out.println("아이디 중복체크");
        boolean result = userService.checkId(loginId);
        // 중복시 true, 중복X false
        // ID 사용가능 result : true
        response.put("result", String.valueOf(!result));
        // ID 사용불가 result : false

        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("아이디 중복체크")
                .data(response)
                .build(), HttpStatus.OK);

    }

    // 닉네임 중복체크
    @RequestMapping(value = "/signup/check-nick/{userNickname}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> checkNickname(@PathVariable String userNickname){
        Map<String, String> response = new HashMap<>();
        System.out.println("닉네임 중복체크");
        boolean result = userService.checkNickname(userNickname);   // 중복시 true, 중복X false
        response.put("result", String.valueOf(!result)); // 닉네임 사용가능 result : true
        System.out.println("check-nickname "+String.valueOf(!result));
        return ResponseEntity.ok(response);              // 닉네임 사용불가 result : false

    }

    // 싸피 학번중복체크
    @RequestMapping(value = "/signup/check-student-num/{userSsafyId}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> checkSsafyId(@PathVariable String userSsafyId){
        Map<String, String> response = new HashMap<>();
        System.out.println("학번체크");
        boolean result = userService.checkSsafyId(Integer.parseInt(userSsafyId));   // 중복시 true, 중복X false

        response.put("result", String.valueOf(!result)); // 학번 사용가능 result : true
        System.out.println("check-ssafyNumber "+String.valueOf(!result));
        return ResponseEntity.ok(response);              // 학번 사용불가 result : false
    }

    // 싸피 핸드폰인증
    @RequestMapping(value = "/signup/check-phoneNum/{userPhone}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> checkPhone(@PathVariable String userPhone){
        Map<String, String> response = new HashMap<>();
        System.out.println("폰 번호 체크");
        boolean result = userService.checkPhone(String.valueOf(userPhone));   // 중복시 true, 중복X false

        response.put("result", String.valueOf(!result)); // 번호 사용가능 result : true
        System.out.println("check-phone "+String.valueOf(!result));
        return ResponseEntity.ok(response);              // 번호 사용불가 result : false
    }

    // 로그인
    @RequestMapping(value = "/login/" ,method = RequestMethod.POST)
    public String login(@RequestBody UserLoginDto userLoginDto){
        return "login경로";
//        Map<String, String> response = new HashMap<>();
//
//        User user = userService.login(userLoginDto);
//
//        if(user==null){
//            if(ResponseEntity.badRequest().equals("500")){
//                return null;
//            }
//            return null;
////            return (ResponseEntity<Map<String, String>>) ResponseEntity.badRequest();
//        }
//        else{
//            response.put("user_id", String.valueOf(user.getUserId()));
//            response.put("login_id", user.getLoginId());
//            response.put("user_ssafy_id", String.valueOf(user.getUserSsafyId()));
//            response.put("user_email", user.getUserEmail());
//            response.put("user_nickname", user.getUserNickname());
//            response.put("user_name", user.getUserName());
//
//            return ResponseEntity.ok(response);
//        }
    }

    // 마이페이지 사용자 정보 표시
    @RequestMapping(value = "/user-info/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> userInfo(UserInfoDto userInfoDto){
        Map<String, String > response = new HashMap<>();

        User user = userService.userInfo(userInfoDto);
        response.put("user_id", String.valueOf(user.getUserId()));
        response.put("user_ssafy_id", String.valueOf(user.getUserSsafyId()));
        response.put("user_nickname", user.getUserNickname());
        response.put("user_img", user.getUserImg());
        response.put("badge_id", String.valueOf(user.getBadgeId()));
        response.put("challeng_cnt", String.valueOf(user.getChallengeCnt()));
        response.put("complain_cnt", String.valueOf(user.getComplainCnt()));

        return ResponseEntity.ok(response);
    }
}
