package com.ssafy.sgdc.feed.dto;

import com.ssafy.sgdc.feed.entity.Feed;
import com.ssafy.sgdc.feed.entity.FeedReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FeedReportDto {
    private int userId;
    private int reportUserId;
    private int feedId;
    private String reportDetail;

    public FeedReportDto(
                         int userId,
                         int reportUserId,
                         int feedId,
                         String reportDetail
                         ) {
        this.userId = userId;
        this.reportUserId = reportUserId;
        this.feedId = feedId;
        this.reportDetail = reportDetail;
    }

    public static FeedReportDto of(
                          int userId,
                          int reportUserId,
                          int feedId,
                          String reportDetail
    )
    { return new FeedReportDto(userId,reportUserId,feedId,reportDetail); }


}
