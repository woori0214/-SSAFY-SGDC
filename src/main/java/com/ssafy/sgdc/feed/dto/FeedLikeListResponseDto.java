package com.ssafy.sgdc.feed.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedLikeListResponseDto {
    private String userNickname;
    private int userId;
    private String userImg;


    public FeedLikeListResponseDto(int userId, String userNickname, String userImg) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userImg = userImg;
    }

}
