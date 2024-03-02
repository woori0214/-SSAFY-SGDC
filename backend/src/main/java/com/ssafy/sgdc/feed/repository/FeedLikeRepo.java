package com.ssafy.sgdc.feed.repository;

import com.ssafy.sgdc.feed.entity.Feed;
import com.ssafy.sgdc.feed.entity.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedLikeRepo extends JpaRepository<FeedLike, Integer> {


    // 피드 좋아요 수
    int countByFeed_FeedId(int feedId);

    // 특정 피드를 좋아요 했는지 확인
    boolean existsByUser_UserIdAndFeed_FeedId(int userId, int feedId);

    // 좋아요 취소
    void deleteByUser_UserIdAndFeed_FeedId(int userId, int feedId);
}
