package com.ssafy.sgdc.feed.dto;

import com.ssafy.sgdc.feed.entity.Feed;
import com.ssafy.sgdc.feed.entity.FeedReport;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedReportDto {
    private int feedReportId;
    private int userId;
    private int reportUserId;
    private int feedId;
    private String reportDetail;

    public FeedReportDto(int feedReportId,
                         int userId,
                         int reportUserId,
                         int feedId,
                         String reportDetail
                         ) {
        this.feedReportId = feedReportId;
        this.userId = userId;
        this.reportUserId = reportUserId;
        this.feedId = feedId;
        this.reportDetail = reportDetail;
    }

    public static FeedReportDto of(int feedReportId,
                          int userId,
                          int reportUserId,
                          int feedId,
                          String reportDetail
    )
    { return new FeedReportDto(feedReportId,userId,reportUserId,feedId,reportDetail); }


}
