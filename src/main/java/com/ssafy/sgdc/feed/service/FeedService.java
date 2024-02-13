package com.ssafy.sgdc.feed.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.sgdc.competition.domain.Competition;
import com.ssafy.sgdc.competition.domain.ImageAuth;
import com.ssafy.sgdc.competition.domain.Matching;
import com.ssafy.sgdc.competition.repository.CompetDetailRepo;
import com.ssafy.sgdc.competition.repository.CompetitionRepo;
import com.ssafy.sgdc.competition.repository.ImageAuthRepo;
import com.ssafy.sgdc.competition.repository.MatchingRepo;
import com.ssafy.sgdc.competition.service.ImageAuthService;
import com.ssafy.sgdc.enums.CompetResult;
import com.ssafy.sgdc.enums.IsSender;
import com.ssafy.sgdc.enums.S3ImageFolder;
import com.ssafy.sgdc.feed.repository.FeedLikeRepo;
import com.ssafy.sgdc.feed.repository.FeedRepo;
import com.ssafy.sgdc.feed.dto.FeedOneDto;
import com.ssafy.sgdc.feed.entity.Feed;
import com.ssafy.sgdc.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {
    private final FeedRepo feedRepo;
    private final CompetitionRepo competitionRepo;
    private final MatchingRepo matchingRepo;
    private final ImageAuthRepo imageAuthRepo;
    private final FeedLikeRepo feedLikeRepo;
    private final ImageAuthService imageAuthService;

    /**
     * 피드 생성
     */
    @Transactional
    public void createFeed(Competition compet) {
        Competition competition = findCompetitionById(compet.getCompetId());

        List<Matching> matches = findMatchesByCompetitionId(compet.getCompetId());
        Matching senderMatching = findMatchingByIsSender(matches, IsSender.Y); // 발신자 도전장
        Matching receiverMatching = findMatchingByIsSender(matches, IsSender.N); // 수신자 도전장

        validateMatchings(senderMatching, receiverMatching);
        CompetResult result = competition.getCompetDetail().getCompetResult();

        if (result.equals(CompetResult.BOTH_FAIL)) {
            throw new RuntimeException("공동 패배하여 피드에 게시물이 올라가지 않습니다.");
        }

        String feedTitle = createFeedTitle(result, senderMatching, receiverMatching); // 타이틀 설정
        String feedImage = findFeedImage(result,senderMatching,receiverMatching); // 이미지 url 설정
        Feed feed = Feed.of(feedTitle, "", 0, 0, false, LocalDateTime.now(), null, feedImage, compet);
        feedRepo.save(feed);
    }


    /**
     * 특정 피드 불러오기
     */

    public FeedOneDto getFeedOne(int feedId, int userId) {
        Feed feed = feedRepo.findByFeedId(feedId)
                .orElseThrow(() -> new RuntimeException("해당 피드를 찾을 수 없습니다."));

        List<Matching> matches = findMatchesByCompetitionId(feed.getCompetId().getCompetId());
        Matching senderMatching = findMatchingByIsSender(matches, IsSender.Y);
        boolean isLiked = feedLikeRepo.existsByUser_UserIdAndFeed_FeedId(userId, feedId);

        return new FeedOneDto(
                feed.getFeedId(),
                feed.getFeedTitle(),
                feed.getFeedContent(),
                feed.getFeedLikeNum(),
                feed.getViews(),
                feed.isPrivacy(),
                feed.getCreateAt(),
                feed.getUpdateAt(),
                feed.getFeedImg(),
                senderMatching.getUser().getUserId(),
                senderMatching.getUser().getUserNickname(),
                senderMatching.getUser().getUserImg(),
                isLiked
        );
    }


    /**
     *  피드 게시물 10개씩 불러오기
     */

    public Page<FeedOneDto> findItemsAfter(int feedId, int userId, Pageable pageable) {
        if (feedId==0) {
            // 처음 페이지를 로드할 때
            Page<Feed> feedPage = feedRepo.findAll(pageable);
            return feedPage.map(item -> convertToDto(item, userId));
        } else {
            // 커서 기반 페이지네이션: lastId 이후의 아이템을 조회
            Page<Feed> feedPage = feedRepo.findByFeedIdGreaterThan(feedId, pageable);
            return feedPage.map(item -> convertToDto(item, userId));
        }
    }


    /**
     * 피드 조회수 업데이트
     */
    @Transactional
    public int updateView(int feedId) {
        feedRepo.updateView(feedId);
        return getFeedViews(feedId);
    }

    private int getFeedViews(int feedId) {
        return feedRepo.findByFeedId(feedId)
                .map(Feed::getViews)
                .orElse(0);
    }


    private FeedOneDto convertToDto(Feed feed, int userId) {
        List<Matching> matches = findMatchesByCompetitionId(feed.getCompetId().getCompetId());
        Matching senderMatching = findMatchingByIsSender(matches, IsSender.Y);
        boolean isLiked = feedLikeRepo.existsByUser_UserIdAndFeed_FeedId(userId, feed.getFeedId());

        // Feed 엔티티의 데이터를 사용하여 FeedOneDto 객체를 생성합니다.
        return new FeedOneDto(
                feed.getFeedId(),
                feed.getFeedTitle(),
                feed.getFeedContent(),
                feed.getFeedLikeNum(),
                feed.getViews(),
                feed.isPrivacy(),
                feed.getCreateAt(),
                feed.getUpdateAt(),
                feed.getFeedImg(),
                // 매칭된 사용자 정보를 설정
                senderMatching.getUser().getUserId(),
                senderMatching.getUser().getUserNickname(),
                senderMatching.getUser().getUserImg(),
                isLiked
        );
    }

    private Matching findMatchingByIsSender(List<Matching> matches, IsSender isSender) {
        return matches.stream()
                .filter(match -> match.getIsSender().equals(isSender))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(isSender + "에 해당하는 Matching을 찾을 수 없습니다."));
    }


    private Competition findCompetitionById(int competId) {
        return competitionRepo.findByCompetId(competId)
                .orElseThrow(() -> new RuntimeException("해당 경쟁을 찾을 수 없습니다."));
    }

    private List<Matching> findMatchesByCompetitionId(int competId) {
        List<Matching> matches = matchingRepo.findByCompetitionCompetId(competId);
        if (matches.isEmpty()) {
            throw new RuntimeException("해당 경쟁의 도전장 결과를 찾을 수 없습니다.");
        }
        return matches;
    }

    private String findImageAuthByMatching(Matching matching) {
        return imageAuthRepo.findByMatcingMatchingId(matching.getMatchingId())
                .map(ImageAuth::getAuthImg)
                .orElse("");
    }

    private void validateMatchings(Matching senderMatching, Matching receiverMatching) {
        if (senderMatching == null || receiverMatching == null) {
            throw new RuntimeException("매칭 정보를 찾을 수 없습니다.");
        }
    }


    private String createFeedTitle(CompetResult result, Matching sender, Matching receiver) {
        String category = sender.getCategory().getCategoryName().toString();
        return switch (result) {
            case SEND_WIN -> String.format("[%s] %s님이 %s님과의 경쟁에서 이겼습니다!",
                    category, sender.getUser().getUserNickname(),
                    receiver.getUser().getUserNickname());
            case RECEIVE_WIN -> String.format("[%s] %s님이 %s님과의 경쟁에서 이겼습니다!",
                    category, receiver.getUser().getUserNickname(),
                    sender.getUser().getUserNickname());
            case BOTH_WIN -> String.format("%s님과 %s님이 공동승리 하였습니다!",
                    sender.getUser().getUserNickname(),
                    receiver.getUser().getUserNickname());
            default -> throw new RuntimeException("CompetResult이 정의되지 않았습니다.");
        };
    }

    private String findFeedImage(CompetResult result, Matching senderMatching, Matching receiverMatching) {
        return switch (result) {
            case SEND_WIN -> findImageAuthByMatching(senderMatching);
            case RECEIVE_WIN -> findImageAuthByMatching(receiverMatching);
            case BOTH_WIN -> findImageAuthByMatching(senderMatching); //// 일단 발신자 사진으로 feed 저장
            default -> "";
        };
    }


    /**
     * 이미지 합치는 메소드
     */

    public void resultImage() throws IOException {
        uploadToS3(mergeImages(),"sgdc-test-bucket","testtestestestestestestestsetset");
    }


    public BufferedImage mergeImages() throws IOException {
        String mergeImageUrl="https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/COMMON/winner_merge_image.png";
        String imageOne="https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/PROFILE_IMAGE/usertest_1.PNG";
        String imageTwo="https://sgdc-test-bucket.s3.ap-northeast-2.amazonaws.com/PROFILE_IMAGE/ssafy123_%EC%A7%80%EC%9D%80.png";
        BufferedImage image1=loadImage(mergeImageUrl);
        BufferedImage image2=loadImage(imageOne);
        BufferedImage image3=loadImage(imageTwo);


        // 이미지 1의 크기를 기준으로 새 이미지 생성
        BufferedImage combined = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = combined.getGraphics();

        // 이미지 겹치기
        g.drawImage(image1, 100, 0, null);
        g.drawImage(image2, 0, 300, null);
        g.drawImage(image3, 0, 0, null);

        g.dispose();

        return combined;
    }

    public void uploadToS3(BufferedImage image, String bucketName, String key) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream); // 이미지를 PNG 포맷으로 변환하여 ByteArrayOutputStream에 쓰기

        byte[] imageBytes = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes); // ByteArrayOutputStream으로부터 ByteArrayInputStream 생성

        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageBytes.length);
        metadata.setContentType("image/png"); // 이미지의 MIME 타입 설정

        // S3에 업로드
        PutObjectRequest request = new PutObjectRequest(bucketName, key, inputStream, metadata);
        s3Client.putObject(request);


        // 사용한 스트림 닫기
        outputStream.close();
        inputStream.close();
    }


    // BufferedImage로 변환
    public static BufferedImage loadImage(String url) throws IOException {
        URL imageUrl = new URL(url);
        return ImageIO.read(imageUrl);
    }




}
