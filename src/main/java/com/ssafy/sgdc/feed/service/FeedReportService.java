package com.ssafy.sgdc.feed.service;


import com.ssafy.sgdc.badge.domain.UserBadge;
import com.ssafy.sgdc.badge.dto.UserBadgeDto;
import com.ssafy.sgdc.badge.repository.BadgeRepo;
import com.ssafy.sgdc.badge.repository.UserBadgeRepo;
import com.ssafy.sgdc.feed.entity.Feed;
import com.ssafy.sgdc.feed.entity.FeedReport;
import com.ssafy.sgdc.feed.repository.FeedRepo;
import com.ssafy.sgdc.feed.repository.FeedReportRepo;
import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedReportService {
    private final FeedReportRepo feedReportRepo;
    private final UserRepo userRepo;
    private final FeedRepo feedRepo;
    private final BadgeRepo badgeRepo;
    private final UserBadgeRepo userBadgeRepo;

    /**
     * 피드 신고하기
     */
    public FeedReport createFeedReport(int userId, int reportUserId, int feedId, String reportDetail) {
        User user = userRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("[FeedReportService][createFeedReport] 신고한 유저를 찾을 수 없습니다."));
        User reportUser = userRepo.findByUserId(reportUserId)
                .orElseThrow(() -> new RuntimeException("[FeedReportService][createFeedReport] 신고당한 유저를 찾을 수 없습니다."));
        Feed feed = feedRepo.findByFeedId(feedId)
                .orElseThrow(() -> new RuntimeException("[FeedReportService][createFeedReport] 해당 피드를 찾을 수 없습니다."));

        FeedReport feedReport = FeedReport.of(0,user,reportUser,feed,reportDetail);

        feedReportRepo.save(feedReport);

        // 신고하는 유저가 신고 얼마나 했는 지 세고 5회면 싸피 보안관 뱃지 추가
        // 신고하는 유저가 싸피 보안관을 가지고 있는 지 확인
        Optional<UserBadge> optionalUserBadge =
                userBadgeRepo.findUserBadgeByUserUserIdAndBadgeBadgeId(
                        userId, 18
                );

        if (optionalUserBadge.isEmpty() && feedReportRepo.countFeedReportsByUserUserId(userId) == 5) {
            userBadgeRepo.save(
                    UserBadgeDto.from(
                            badgeRepo.findBadgeByBadgeId(18)
                                    .orElseThrow(() -> new RuntimeException("해당 뱃지를 찾을 수 없습니다.")),
                            user
                    )
            );
        }

        // 신고 당한 유저가 신고 얼마나 했는 지 세고 3회면 싸피 피노키오 뱃지 추가
        // 신고 당한 유저가 피노키오를 가지고 있는 지 확인
        Optional<UserBadge> optionalReportUserBadge =
                userBadgeRepo.findUserBadgeByUserUserIdAndBadgeBadgeId(
                        reportUserId, 17
                );

        if (optionalReportUserBadge.isEmpty() && feedReportRepo.countFeedReportsByReportUserUserId(reportUserId) == 3) {
            userBadgeRepo.save(
                    UserBadgeDto.from(
                            badgeRepo.findBadgeByBadgeId(17)
                                    .orElseThrow(() -> new RuntimeException("해당 뱃지를 찾을 수 없습니다.")),
                            reportUser
                    )
            );
        }

        return feedReport;
    }
}
