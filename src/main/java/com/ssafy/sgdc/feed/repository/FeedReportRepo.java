package com.ssafy.sgdc.feed.repository;

import com.ssafy.sgdc.feed.entity.FeedReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedReportRepo extends JpaRepository<FeedReport, Integer> {

    int countFeedReportsByUserUserId(int userId);

    int countFeedReportsByReportUserUserId(int userId);

}
