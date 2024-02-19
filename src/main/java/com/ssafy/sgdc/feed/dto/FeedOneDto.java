package com.ssafy.sgdc.feed.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedOneDto {
    private int feedId;
    private String feedTitle;
    private String feedContent;
    private int feedLikeNum;
    private int views;
    private boolean privacy;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String feedImg;
    private int winnerUserId;
    private String winnerUserNickname;
    private String winnerUserImg;
    private String winnerUserBadgeImg;
    private int loserUserId;
    private String loserUserNickname;
    private String loserUserImg;
    private String loserUserBadgeImg;
    private boolean isLiked;
    public FeedOneDto(int feedId,
                      String feedTitle,
                      String feedContent,
                      int feedLikeNum,
                      int views,
                      boolean privacy,
                      LocalDateTime createAt,
                      LocalDateTime updateAt,
                      String feedImg,
                      int winnerUserId,
                      String winnerUserNickname,
                      String winnerUserImg,
                      String winnerUserBadgeImg,
                      int loserUserId,
                      String loserUserNickname,
                      String loserUserImg,
                      String loserUserBadgeImg,
                      boolean isLiked) {
        this.feedId = feedId;
        this.feedTitle = feedTitle;
        this.feedContent = feedContent;
        this.feedLikeNum = feedLikeNum;
        this.views = views;
        this.privacy = privacy;
        this.createAt = createAt; 
        this.updateAt = updateAt;
        this.feedImg = feedImg;
        this.winnerUserId = winnerUserId;
        this.winnerUserNickname = winnerUserNickname;
        this.winnerUserImg = winnerUserImg;
        this.winnerUserBadgeImg = winnerUserBadgeImg;
        this.loserUserId = loserUserId;
        this.loserUserNickname = loserUserNickname;
        this.loserUserImg = loserUserImg;
        this.loserUserBadgeImg = loserUserBadgeImg;
        this.isLiked = isLiked;
    }
}
