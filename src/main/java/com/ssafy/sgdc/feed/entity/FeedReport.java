package com.ssafy.sgdc.feed.entity;

import com.ssafy.sgdc.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feed_report")
public class FeedReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_report_id")
    private int feedReportId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "report_user_id", referencedColumnName = "user_id")
    private User reportUser;

    @ManyToOne
    @JoinColumn(name = "report_feed_id", referencedColumnName = "feed_id")
    private Feed reportFeed;

    @Column(name = "report_detail")
    private String reportDetail;


    public static FeedReport of(int feedReportId,
                                   User user,
                                   User reportUser,
                                   Feed feed,
                                   String reportDetail
    )
    { return new FeedReport(feedReportId,user,reportUser,feed,reportDetail); }


}