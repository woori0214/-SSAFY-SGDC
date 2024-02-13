package com.ssafy.sgdc.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.sgdc.base.dto.DataResponseDto;
import com.ssafy.sgdc.enums.S3ImageFolder;
import com.ssafy.sgdc.user.dto.*;
import com.ssafy.sgdc.util.JwtUtil;
import com.ssafy.sgdc.util.response.Code;
import com.ssafy.sgdc.util.response.CustomException;
import com.ssafy.sgdc.util.response.GeneralResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private String bucketName = "sgdc-test-bucket"; // S3 버킷 이름

    @Operation(summary = "서버 접속")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getRoot() {
        System.out.println("user경로입니다.");
        return "Hello User";
    }

    @Operation(summary = "회원가입")
    @RequestMapping(value = "/signup/", method = RequestMethod.POST)
    public ResponseEntity<GeneralResponse> signUp(
            @RequestPart String userSignUpJson,
            @RequestPart(value = "profileImage") MultipartFile profile
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserSignUpDto userSignUpDto = objectMapper.readValue(userSignUpJson, UserSignUpDto.class);
        String ImageUrl=userService.uploadS3(userSignUpDto.getLoginId(),profile, S3ImageFolder.PROFILE_IMAGE);
        userService.signUp(userSignUpDto,ImageUrl);

        Map<String, String> result = new HashMap<>();
        result.put("result", "true");

        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("회원가입")
                .data(result)
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "아이디 중복검사")
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

    @Operation(summary = "닉네임 중복검사")
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

    @Operation(summary = "싸피 학번 중복검사")
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

    @Operation(summary = "폰 번호 중복검사")
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

    @Operation(summary = "싸피 이메일 중복검사")
    @RequestMapping(value = "/signup/check-student-email/{userSsafyEmail}", method = RequestMethod.GET)
    public ResponseEntity<GeneralResponse> checkSsafyEmail(@PathVariable String userSsafyEmail) {
        Map<String, String> response = new HashMap<>();
        boolean result = userService.checkSsafyEmail(userSsafyEmail);   // 중복시 true, 중복X false
        response.put("result", String.valueOf(!result));

        // 이메일 사용가능 result : true
        // 이메일 사용불가 result : false
        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("이메일 중복체크")
                .data(response)
                .build(), HttpStatus.OK);
    }

    @Operation(summary = "로그인")
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

    @Operation(summary = "토큰 만료 전", description="토큰만료 전 토큰을 재발급합니다.")
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

    @Operation(summary = "마이페이지 정보 확인", description="마이페이지 정보를 표시합니다.")
    @RequestMapping(value = "/user-info/{userId}", method = RequestMethod.GET)
    public ResponseEntity<GeneralResponse> userInfo(@PathVariable User userId) {
        Map<String, String> response = new HashMap<>();

        User user = userService.userInfo(userId);
        response.put("user_id", String.valueOf(user.getUserId()));
        response.put("user_ssafy_id", String.valueOf(user.getUserSsafyId()));
        response.put("user_nickname", user.getUserNickname());
        response.put("user_img", user.getUserImg());
        response.put("badge_id", String.valueOf(user.getBadgeId().getBadgeId()==0?0:1));
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

    @Operation(summary = "마이페이지 수정", description="닉네임, 폰, 프로필, 대표뱃지를 수정할 수 있습니다.")
    @RequestMapping(value = "/user-info-modify/{userId}", method = RequestMethod.PATCH)
    public ResponseEntity<GeneralResponse> userInfoModify(@RequestBody UserInfoModifyDto userInfoModifyDto) {
        Map<String, String> response = new HashMap<>();
        System.out.println("회원수정");

        User user = userService.modifyUser(userInfoModifyDto);
         response.put("user_nickName", userInfoModifyDto.getUserNickname());
         response.put("user_phone", userInfoModifyDto.getUserPhone());
         if(user.getBadgeId()==null){
             response.put("badge_id", null);
         }
         else {
             response.put("badge_id", String.valueOf(userInfoModifyDto.getBadgeId()));
         }
        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("마이페이지 수정")
                .data(response)
                .build(), HttpStatus.OK);
    }
    @Operation(summary = "프로필 사진 수정", description="프로필사진을 변경할 수 있습니다.")
    @RequestMapping(value = "/user-profile-modify/{userId}", method = RequestMethod.PATCH)
    public ResponseEntity<GeneralResponse> userInfoModify(
            @PathVariable int userId,
            @RequestPart(value = "profileImage") MultipartFile profile
            ) throws JsonProcessingException {

        User findUser = userService.getUserById(userId);
        User user = userService.deleteProfile(findUser);

        String ImageUrl = userService.uploadS3(findUser.getLoginId(), profile, S3ImageFolder.PROFILE_IMAGE);

        userService.updateProfile(user, ImageUrl);

        Map<String, String> response = new HashMap<>();

        System.out.println("프로필 사진수정완료");
        return new ResponseEntity<>(GeneralResponse.builder()
                .status(200)
                .message("프로필 사진수정")
                .data(response)
                .build(), HttpStatus.OK);

    }
}


