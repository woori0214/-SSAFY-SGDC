package com.ssafy.sgdc.feed.service;

import com.ssafy.sgdc.feed.dto.FeedLikeListResponseDto;
import com.ssafy.sgdc.feed.entity.Feed;
import com.ssafy.sgdc.feed.entity.FeedLike;
import com.ssafy.sgdc.feed.repository.FeedLikeRepo;
import com.ssafy.sgdc.feed.repository.FeedRepo;
import com.ssafy.sgdc.follow.Follow;
import com.ssafy.sgdc.follow.dto.FollowingListResponseDto;
import com.ssafy.sgdc.user.User;
import com.ssafy.sgdc.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
        User user = userRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("toggleList -> 해당 유저를 찾을 수 없습니다."));
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


    /**
     * 피드 좋아요 리스트
     */
    public List<FeedLikeListResponseDto> feedLikeList(int feedId) {
        List<FeedLike> feedLikeList=feedLikeRepo.getListByFeedId(feedId);
        List<FeedLikeListResponseDto> feedLikeDtoList = new ArrayList<>();
        for (FeedLike feedLike : feedLikeList) {
            User userInfo = feedLike.getUser();
            feedLikeDtoList.add(new FeedLikeListResponseDto(userInfo.getUserId(),userInfo.getUserNickname(),userInfo.getUserImg()));
        }
        return feedLikeDtoList;
    }


}