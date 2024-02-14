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
    private int userId;
    private String userNickname;
    private String userImg;
    private String userBadgeImg;
    private boolean isLiked;
    public FeedOneDto(int feedId, String feedTitle, String feedContent, int feedLikeNum, int views, boolean privacy, LocalDateTime createAt, LocalDateTime updateAt, String feedImg, int userId, String userNickname, String userImg, String userBadgeImg, boolean isLiked) {
        this.feedId = feedId;
        this.feedTitle = feedTitle;
        this.feedContent = feedContent;
        this.feedLikeNum = feedLikeNum;
        this.views = views;
        this.privacy = privacy;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.feedImg = feedImg;
        this.userId = userId;
        this.userNickname = userNickname;
        this.userImg = userImg;
        this.userBadgeImg = userBadgeImg;
        this.isLiked = isLiked;
    }
}
