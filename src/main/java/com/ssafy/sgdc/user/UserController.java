package com.ssafy.sgdc.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.sgdc.base.dto.DataResponseDto;
import com.ssafy.sgdc.user.dto.*;
import com.ssafy.sgdc.util.JwtUtil;
import com.ssafy.sgdc.util.response.Code;
import com.ssafy.sgdc.util.response.CustomException;
import com.ssafy.sgdc.util.response.GeneralResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwt;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getRoot() {
        System.out.println("user경로입니다.");
        return "Hello User";
    }

    @RequestMapping(value = "/signup/", method = RequestMethod.POST)
    public ResponseEntity<GeneralResponse> signUp(@RequestBody UserSignUpDto userSignUpDto) {
        System.out.println("회원가입 경로.");
        userService.signUp(userSignUpDto);

        Map<String, String> result = new HashMap<>();
        result.put("result", "true");

        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("회원가입")
                .data(result)
                .build(), HttpStatus.OK);
    }

    // 아이디 중복체크
    @RequestMapping(value = "/signup/check-id/{loginId}", method = RequestMethod.GET)
    public ResponseEntity<GeneralResponse> checkId(@PathVariable String loginId) {
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
    public ResponseEntity<GeneralResponse> checkNickname(@PathVariable String userNickname) {
        Map<String, String> response = new HashMap<>();
        System.out.println("닉네임 중복체크");
        boolean result = userService.checkNickname(userNickname);   // 중복시 true, 중복X false
        response.put("result", String.valueOf(!result)); // 닉네임 사용가능 result : true
        System.out.println("check-nickname " + String.valueOf(!result));
//        return ResponseEntity.ok(response);              // 닉네임 사용불가 result : false

        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("닉네임 중복체크")
                .data(response)
                .build(), HttpStatus.OK);
    }

    // 싸피 학번중복체크
    @RequestMapping(value = "/signup/check-student-num/{userSsafyId}", method = RequestMethod.GET)
    public ResponseEntity<GeneralResponse> checkSsafyId(@PathVariable String userSsafyId) {
        Map<String, String> response = new HashMap<>();
        System.out.println("학번체크");
        boolean result = userService.checkSsafyId(Integer.parseInt(userSsafyId));   // 중복시 true, 중복X false

        response.put("result", String.valueOf(!result));
        System.out.println("check-ssafyNumber " + String.valueOf(!result));

        // 학번 사용가능 result : true
        // 학번 사용불가 result : false
        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("학번 중복체크")
                .data(response)
                .build(), HttpStatus.OK);
    }

    // 싸피 핸드폰인증
    @RequestMapping(value = "/signup/check-phoneNum/{userPhone}", method = RequestMethod.GET)
    public ResponseEntity<GeneralResponse> checkPhone(@PathVariable String userPhone) {
        Map<String, String> response = new HashMap<>();
        System.out.println("폰 번호 체크");
        boolean result = userService.checkPhone(String.valueOf(userPhone));   // 중복시 true, 중복X false

        response.put("result", String.valueOf(!result));
        System.out.println("check-phone " + String.valueOf(!result));
        // 번호 사용가능 result : true
        // 번호 사용불가 result : false
        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("폰 번호 중복체크")
                .data(response)
                .build(), HttpStatus.OK);
    }

    // 로그인
    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public ResponseEntity<GeneralResponse> login(@RequestBody UserLoginDto userLoginDto) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();

        User user = userService.login(userLoginDto);

        if (user == null) {
            throw new CustomException(Code.INVALID_ID_PW);
        } else {
            String accessToken = jwt.generateAccessToken(user);
            String refreshToken = jwt.generateRefreshToken(user);

            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);
            response.put("user_id", String.valueOf(user.getUserId()));
            response.put("login_id", user.getLoginId());
            response.put("user_ssafy_id", String.valueOf(user.getUserSsafyId()));
            response.put("user_email", user.getUserEmail());
            response.put("user_nickname", user.getUserNickname());
            response.put("user_name", user.getUserName());

            return new ResponseEntity<>(GeneralResponse.builder()
                    .status(200)
                    .message("로그인")
                    .data(response)
                    .build(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/re-auth/", method = RequestMethod.POST)
    public ResponseEntity<GeneralResponse> reAuth(@RequestBody TokenDto tokenDto) {
        User user = userService.getUserById(tokenDto.getUserId());
        if (user != null && jwt.validateToken(tokenDto.getRefreshToken(), user)) { //refreshToken 검증 완료
            String accessToken = jwt.generateAccessToken(user);
            String refreshToken = jwt.generateRefreshToken(user);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);

            return new ResponseEntity<>(GeneralResponse.builder()
                    .status(200)
                    .message("토큰 재발급 완료")
                    .data(response)
                    .build(), HttpStatus.OK);
        } else {
            throw new CustomException(Code.INVALID_TOKEN);
        }
    }

    //대표뱃지
//    @RequestMapping(value = "/{userId}/{badgeId}", method = RequestMethod.PATCH)
//    public ResponseEntity<GeneralResponse> userMainBadge(@RequestBody UserBadge badgeId) {
//        Map<String,String> response = new HashMap<>();
//
//        User user = userService.
//                response.put("badge_id", user.)
//    }

    // 마이페이지 사용자 정보 표시
    @RequestMapping(value = "/user-info/{userId}", method = RequestMethod.GET)
    public ResponseEntity<GeneralResponse> userInfo(UserInfoDto userInfoDto) {
        Map<String, String> response = new HashMap<>();

        User user = userService.userInfo(userInfoDto);
        response.put("user_id", String.valueOf(user.getUserId()));
        response.put("user_ssafy_id", String.valueOf(user.getUserSsafyId()));
        response.put("user_nickname", user.getUserNickname());
        response.put("user_img", user.getUserImg());
        response.put("badge_id", String.valueOf(user.getBadgeId()));
        response.put("challeng_cnt", String.valueOf(user.getChallengeCnt()));
        response.put("user_phone", String.valueOf(user.getUserPhone()));
        response.put("complain_cnt", String.valueOf(user.getComplainCnt()));

        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("내 프로필 확인")
                .data(response)
                .build(), HttpStatus.OK);
    }
    /**
     * 유저 닉네임 검색
     */
    @Operation(summary = "닉네임 검색", description="닉네임을 검색합니다.")
    @Parameter(name = "keyword", schema = @Schema(implementation = String.class), description = "사용자가 검색하는 키워드", in = ParameterIn.QUERY)
    @GetMapping("/search-nickname")
    public DataResponseDto<Page<SearchNameResponseDto>> searchNickname (
            @RequestParam(value="keyword",required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "userNickname");
        return DataResponseDto.of(userService.searchNickname(keyword,pageable), "닉네임 검색");
    }

}
