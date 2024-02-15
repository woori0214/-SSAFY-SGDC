package com.ssafy.sgdc.competition.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.sgdc.badge.service.BadgeService;
import com.ssafy.sgdc.category.UserCategory;
import com.ssafy.sgdc.category.repository.UserCategoryRepo;
import com.ssafy.sgdc.competition.domain.CompetDetail;
import com.ssafy.sgdc.competition.domain.Competition;
import com.ssafy.sgdc.competition.domain.Matching;
import com.ssafy.sgdc.competition.dto.request.CreateCompetDetailDto;
import com.ssafy.sgdc.competition.dto.request.CreateImageAuthDto;
import com.ssafy.sgdc.competition.repository.CompetDetailRepo;
import com.ssafy.sgdc.competition.repository.CompetitionRepo;
import com.ssafy.sgdc.competition.repository.ImageAuthRepo;
import com.ssafy.sgdc.competition.repository.MatchingRepo;
import com.ssafy.sgdc.enums.CategoryStatus;
import com.ssafy.sgdc.enums.CompetResult;
import com.ssafy.sgdc.enums.S3ImageFolder;
import com.ssafy.sgdc.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ImageAuthService {

    private final MatchingRepo matchingRepo;
    private final ImageAuthRepo imageAuthRepo;
    private final CompetitionRepo competitionRepo;
    private final CompetDetailRepo competDetailRepo;
    private final UserCategoryRepo userCategoryRepo;
    private final BadgeService badgeService;
    private final FeedService feedService;

    private String bucketName="sgdc-test-bucket"; // S3 버킷 이름

    @Autowired
    AmazonS3 amazonS3Client; // S3에 업로드를 위한 서비스

    // 이미지 저장
    @Transactional
    public String saveImageAuth(int userId, int competId, MultipartFile authImg) {


        Competition competition = competitionRepo.findByCompetId(competId)
                .orElseThrow(() -> new RuntimeException("경쟁을 찾을 수 없습니다."));

        if (competition.getDoneAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("이미 종료된 경쟁입니다.");
        }

        Matching matching = matchingRepo.findByCompetitionCompetIdAndUserUserId(competId, userId)
                .orElseThrow(() -> new RuntimeException("해당 도전장을 찾을 수 없습니다."));


        // S3 업로드
        String authImgUrl = uploadS3(userId,competId,authImg,S3ImageFolder.COMPETITION_AUTH_IMAGE);

        // 이미지 경로 없을 때 처리
        if (authImgUrl.isEmpty()) {
            throw new RuntimeException("이미지 경로 없음");
        }

        // imageAuth DB에 저장
        CreateImageAuthDto imageAuthDto = new CreateImageAuthDto(authImgUrl, LocalDateTime.now(), competition, matching);
        imageAuthRepo.save(CreateImageAuthDto.from(imageAuthDto));

        // 두 사용자 모두 인증했으면 경쟁 상태 업데이트
        // TODO: 두 명다 인증했을 때도 피드게시판에 올라가야함
        if (imageAuthRepo.countByCompetitionCompetId(competId) == 2) {
            CreateCompetDetailDto competDetailDto = new CreateCompetDetailDto(CompetResult.BOTH_WIN);

            // 유저 카테고리 업데이트
            UserCategory sendUserCategory = userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(
                    userId, matching.getCategory().getCategoryId()
            ).orElseThrow(() -> new RuntimeException("해당 유저의 유저카테고리가 존재하지 않습니다."));

            Matching otherMatching = matchingRepo.findOtherMatching(competId, matching.getMatchingId())
                    .orElseThrow(() -> new RuntimeException("상대방 도전장이 없습니다."));

            UserCategory receiveUserCategory = userCategoryRepo.findUserCategoryByUserUserIdAndAndCategoryCategoryId(
                    otherMatching.getUser().getUserId(), otherMatching.getCategory().getCategoryId()
            ).orElseThrow(() -> new RuntimeException("해당 유저의 유저카테고리가 존재하지 않습니다."));

            sendUserCategory.increaseWinCount();
            receiveUserCategory.increaseWinCount();

            CompetDetail competDetail = CreateCompetDetailDto.from(competDetailDto);
            competDetailRepo.save(competDetail);

            // 유저 카테고리 업데이트
            sendUserCategory.updateCategoryStatus(CategoryStatus.NONE_STATUS);
            receiveUserCategory.updateCategoryStatus(CategoryStatus.NONE_STATUS);

            competition.updateCompetitionDetail(competDetail);
            competition.updateCompetitionDoneAt(LocalDateTime.now());

            // 끝난 경기에 뱃지 부여
            badgeService.branchCategory(matching.getUser().getUserId(), matching.getCategory().getCategoryId());
            badgeService.branchCategory(otherMatching.getUser().getUserId(), otherMatching.getCategory().getCategoryId());
            feedService.createFeed(competition);
        }
        return authImgUrl;
    }

    public String uploadS3(int userId, int typeId, MultipartFile authImg, S3ImageFolder folderName){
        // S3 연결 서비스
        String imageName = StringUtils.deleteWhitespace(folderName+"/"+userId+"_"+typeId+"_"+authImg.getOriginalFilename()); // 파일 이름
        long size = authImg.getSize(); // 파일 크기
        String imagePath= "";

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(authImg.getContentType());
        objectMetaData.setContentLength(size);

        try {

            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, imageName, authImg.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );

            imagePath = amazonS3Client.getUrl(bucketName, imageName).toString(); // 접근가능한 URL 가져오기
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리: 업로드 실패 시
        }
        return imagePath;
    }

}
