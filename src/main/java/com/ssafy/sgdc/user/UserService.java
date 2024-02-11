package com.ssafy.sgdc.user;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.ssafy.sgdc.badge.domain.Badge;
import com.ssafy.sgdc.badge.repository.BadgeRepo;
import com.ssafy.sgdc.user.dto.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.sgdc.enums.S3ImageFolder;
import com.ssafy.sgdc.user.dto.SearchNameResponseDto;
import com.ssafy.sgdc.user.dto.UserInfoDto;
import com.ssafy.sgdc.user.dto.UserLoginDto;
import com.ssafy.sgdc.user.dto.UserSignUpDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final BadgeRepo badgeRepo;

    private final AmazonS3 amazonS3Client; // S3에 업로드를 위한 서비스
    private String bucketName = "sgdc-test-bucket"; // S3 버킷 이름

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User signUp(UserSignUpDto userSignDto, String profileImageUrl) {
        String imageName = StringUtils.deleteWhitespace(S3ImageFolder.PROFILE_IMAGE + "/" + userSignDto.getLoginId() + "_" + profileImageUrl); // 파일 이름

        User user = User.builder()
                .userId(0)
                .loginId(userSignDto.getLoginId())
                .userSsafyId(userSignDto.getUserSsafyId())
                .userEmail(userSignDto.getUserEmail())
                .userNickname(userSignDto.getUserNickname())
                .userName(userSignDto.getUserName())
                .userPhone(userSignDto.getUserPhone())
                .userPassword(passwordEncoder.encode(userSignDto.getUserPassword()))
                .userImg(imageName)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .signOut(false)
                .badgeId(null) //보류
                .kakaoPush(userSignDto.getKakaoPush())
                .challengeCnt(3)
                .complainCnt(0)
                .auth("ROLE_USER").build();
        System.out.println("ssafy_user 확인===>");
        return userRepo.save(user);
    }

    @Transactional
    public boolean checkId(String loginId) {
        boolean loginIdDuplicate = userRepo.existsByLoginId(loginId);
        return loginIdDuplicate;
    }

    @Transactional
    public boolean checkNickname(String userNickname) {
        boolean nickNameDuplicate = userRepo.existsByUserNickname(userNickname);
        return nickNameDuplicate;
    }

    @Transactional
    public boolean checkSsafyId(int userSsafyId) {
        boolean ssafyIdDuplicate = userRepo.existsByUserSsafyId(userSsafyId);
        return ssafyIdDuplicate;
    }

    @Transactional
    public boolean checkPhone(String userPhone) {
        boolean userPhoneDuplicate = userRepo.existsByUserPhone(userPhone);
        return userPhoneDuplicate;
    }

    // TODO : 아이디를 찾지 못하면 orElseThrow로 런타임 에러 띄우고 else를 하는 게 아닌 로직 진행
    public User login(UserLoginDto userLoginDto) {
        User userLoginId = userRepo.findByLoginId(userLoginDto.getLoginId())
                .orElseThrow(() -> new RuntimeException("아이디 없음"));

        if (userLoginId == null) {
            System.out.println("아이디 못찾음");
            return null;
        } else { // 아이디 존재
            // 암호화 패스워드
            if (passwordEncoder.matches(userLoginDto.getUserPassword(), userLoginId.getUserPassword())) {
                System.out.println("로그인 성공");
                return userLoginId;

            } else {
                System.out.println("아이디와 패스워드가 맞지 않습니다.");
            }

            return null;
        }
    }

    public User userInfo(UserInfoDto userInfoDto) {
        User userInfo = userRepo.findByUserId(userInfoDto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));
        return userInfo;
    }

    // TODO : 일단 없으면 null로 로직 수정 필요
    public User getUserById(int userId) {
        User user = userRepo.findByUserId(userId)
                .orElse(null);
        return user;
    }

    /**
     * 유저 닉네임 검색
     */
    @Transactional
    public Page<SearchNameResponseDto> searchNickname(String keyword, Pageable pageable) {

        Page<User> userPage = userRepo.findByUserNicknameContaining(keyword, pageable);
        return userPage.map(this::convertToSearchNicknameDto);
    }

    private SearchNameResponseDto convertToSearchNicknameDto(User user) {
        return new SearchNameResponseDto(
                user.getUserId(),
                user.getUserNickname()
        );
    }

    // 회원수정
    @Transactional
    public User modifyUser(UserInfoModifyDto userInfoModifyDto) {
        // TODO: 뱃지가 아니라 해당 유저가 가지고 있는 뱃지에서 찾도록 해야됨
        Badge badge = badgeRepo.findBadgeByBadgeId(userInfoModifyDto.getBadgeId())
                .orElseThrow(() -> new RuntimeException("modifyUser -> 해당 뱃지를 찾을 수 없습니다."));
        User user = userRepo.findByUserId(userInfoModifyDto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));

        user.setUserNickname(userInfoModifyDto.getUserNickname());
        user.setUserPhone(userInfoModifyDto.getUserPhone());
        user.setBadgeId(badge);
        return user;
    }

    /**
     * 프로필 이미지 S3 업로드
     */

    public String uploadS3(String userLoginId, MultipartFile profileImage, S3ImageFolder folderName) {
        // S3 연결 서비스
        String imageName = StringUtils.deleteWhitespace(folderName + "/" + userLoginId + "_" + profileImage.getOriginalFilename()); // 파일 이름
        long size = profileImage.getSize(); // 파일 크기
        String imagePath = "";

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(profileImage.getContentType());
        objectMetaData.setContentLength(size);

        try {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, imageName, profileImage.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
            imagePath = amazonS3Client.getUrl(bucketName, imageName).toString(); // 접근가능한 URL 가져오기

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("이미지 업로드 실패");
        }
        return imagePath;
    }


    // 프로필 사진 DB수정
    public User updateProfile(User user, String imgName) {

        String updateImgName = StringUtils.deleteWhitespace(S3ImageFolder.PROFILE_IMAGE + "/" + user.getLoginId() + "_" + imgName); // 파일 이름

        user.setUserImg(updateImgName);
        return userRepo.save(user);

    }

    // 프로필 사진 수정(1.삭제, 2.업로드)
    public User deleteProfile(User user) {

        // 사진 삭제
        String curUserImg = user.getUserImg();
        String userImgPath = StringUtils.deleteWhitespace(curUserImg); // 파일 이름

        if (userImgPath != null && !userImgPath.isEmpty()) {
            // S3에서 이미지 파일 삭제
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, userImgPath));
            updateProfile(user, null);
        }
        else {
            // 이미지 파일 경로가 없거나 이미 삭제된 경우 처리
            System.out.println("삭제할 프로필 이미지가 없습니다.");

        }
        return user;

    }

}