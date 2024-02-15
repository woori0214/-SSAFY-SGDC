package com.ssafy.sgdc.feed.repository;


import com.ssafy.sgdc.feed.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedRepo extends JpaRepository<Feed, Integer> {

    // 피드 게시물 정보 표시
    Optional<Feed> findByFeedId(int feedId);


    // 피드 게시물 리스트 표시(10개씩)
    Page<Feed> findAll(Pageable pageable);

    // 피드 게시물 조회수 업데이트
    @Modifying
    @Query("UPDATE Feed f SET f.views=f.views+1 WHERE f.feedId= :feedId")
    int updateView(int feedId);


    // 커서 기반 페이지네이션
    @Query("SELECT f FROM Feed f WHERE (:feedId = 0 OR f.feedId < :feedId) ORDER BY f.feedId DESC")
    Page<Feed> findByFeedIdLessThan(@Param("feedId") int feedId, Pageable pageable);

    // 피드 좋아요 수 업데이트
    @Modifying
    @Query("UPDATE Feed f SET f.feedLikeNum= :feedLikeCount WHERE f.feedId= :feedId")
    int updateLike(int feedId, int feedLikeCount);

}
