package com.ssafy.sgdc.feed.service;

import com.ssafy.sgdc.feed.entity.Feed;
import com.ssafy.sgdc.feed.entity.FeedLike;
import com.ssafy.sgdc.feed.repository.FeedLikeRepo;
import com.ssafy.sgdc.feed.repository.FeedRepo;
import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedLikeService {
    private final UserRepo userRepo;
    private final FeedRepo feedRepo;
    private final FeedLikeRepo feedLikeRepo;

    /**
     * 피드 좋아요 기능
     */
    @Transactional
    public String toggleLike(int userId, int feedId) {
        User user = userRepo.findByUserId(userId);
        Optional<Feed> feed = feedRepo.findByFeedId(feedId);
        if (feedLikeRepo.existsByUser_UserIdAndFeed_FeedId(userId, feedId)) {
            feedLikeRepo.deleteByUser_UserIdAndFeed_FeedId(userId, feedId);
            feedRepo.updateLike(feedId,feedLikeRepo.countByFeed_FeedId(feed.get().getFeedId())); // feed_like_num 업데이트
            return "피드 좋아요 삭제";
        } else {
            FeedLike feedLike = FeedLike.of(user,feed.get());
            feedLikeRepo.save(feedLike);
            feedRepo.updateLike(feedId,feedLikeRepo.countByFeed_FeedId(feed.get().getFeedId())); // feed_like_num 업데이트
            return "피드 좋아요 생성";
        }
    }


}