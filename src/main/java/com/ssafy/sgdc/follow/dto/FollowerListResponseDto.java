package com.ssafy.sgdc.follow.dto;

import com.ssafy.sgdc.follow.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowerListResponseDto {

    private String userNickname;
    private int userId;
    private String userImg;


    public FollowerListResponseDto(int userId, String userNickname, String userImg) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userImg = userImg;
    }

}