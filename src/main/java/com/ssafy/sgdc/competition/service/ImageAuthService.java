package com.ssafy.sgdc.competition.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.sgdc.competition.domain.CompetDetail;
import com.ssafy.sgdc.competition.domain.Competition;
import com.ssafy.sgdc.competition.domain.Matching;
import com.ssafy.sgdc.competition.dto.request.CreateCompetDetailDto;
import com.ssafy.sgdc.competition.dto.request.CreateImageAuthDto;
import com.ssafy.sgdc.competition.repository.CompetDetailRepo;
import com.ssafy.sgdc.competition.repository.CompetitionRepo;
import com.ssafy.sgdc.competition.repository.ImageAuthRepo;
import com.ssafy.sgdc.competition.repository.MatchingRepo;
import com.ssafy.sgdc.enums.CompetResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

    private String bucketName="sgdc-test-bucket"; // S3 버킷 이름

    @Autowired
    AmazonS3 amazonS3Client; // S3에 업로드를 위한 서비스

    // 이미지 저장
    public String saveImageAuth(int userId, int competId, MultipartFile authImg) {


        Competition competition = competitionRepo.findByCompetId(competId)
                .orElseThrow(() -> new RuntimeException("경쟁을 찾을 수 없습니다."));

        if (competition.getDoneAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("이미 종료된 경쟁입니다.");
        }

        Matching matching = matchingRepo.findByCompetitionCompetIdAndUserUserId(competId, userId)
                .orElseThrow(() -> new RuntimeException("해당 도전장을 찾을 수 없습니다."));


        // S3 업로드
        String authImgUrl = uploadS3(userId,competId,authImg);

        // 이미지 경로 없을 때 처리
        if (authImgUrl.isEmpty()) {
            throw new RuntimeException("이미지 경로 없음");
        }

        // imageAuth DB에 저장
        CreateImageAuthDto imageAuthDto = new CreateImageAuthDto(authImgUrl, LocalDateTime.now(), competition, matching);
        imageAuthRepo.save(CreateImageAuthDto.from(imageAuthDto));


        // 두 사용자 모두 인증했으면 경쟁 상태 업데이트
        if (imageAuthRepo.countByCompetitionCompetId(competId) == 2) {
            CreateCompetDetailDto competDetailDto = new CreateCompetDetailDto(CompetResult.BOTH_WIN);

            CompetDetail competDetail = CreateCompetDetailDto.from(competDetailDto);
            competDetailRepo.save(competDetail);

            competition.setCompetDetail(competDetail);
            competitionRepo.save(competition);
        }
        return authImgUrl;
    }

    public String uploadS3(int userId, int competId, MultipartFile authImg){
        // S3 연결 서비스
        String imageName = StringUtils.deleteWhitespace(userId+"_"+competId+"_"+authImg.getOriginalFilename()); // 파일 이름
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
