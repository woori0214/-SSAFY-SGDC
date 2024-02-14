package com.ssafy.sgdc.feed.service;


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

@Service
@RequiredArgsConstructor
@Transactional
public class FeedReportService {
    private final FeedReportRepo feedReportRepo;
    private final UserRepo userRepo;
    private final FeedRepo feedRepo;

    /**
     * 피드 신고하기
     */
    public FeedReport createFeedReport(int userId, String reportUserNickname, int feedId, String reportDetail) {
        User user = userRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("[FeedReportService][createFeedReport] 신고한 유저를 찾을 수 없습니다."));
        User reportUser = userRepo.findByUserNickname(reportUserNickname)
                .orElseThrow(() -> new RuntimeException("[FeedReportService][createFeedReport] 신고당한 유저를 찾을 수 없습니다."));
        Feed feed = feedRepo.findByFeedId(feedId)
                .orElseThrow(() -> new RuntimeException("[FeedReportService][createFeedReport] 해당 피드를 찾을 수 없습니다."));

        FeedReport feedReport = FeedReport.of(0,user,reportUser,feed,reportDetail);

        feedReportRepo.save(feedReport);

        return feedReport;
    }
}
