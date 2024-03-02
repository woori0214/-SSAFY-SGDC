package com.ssafy.sgdc.user.dto;

import lombok.Data;

@Data
public class SearchNameResponseDto {
    private int userId;
    private String userNickname;
    private String userImg;

    public SearchNameResponseDto(int userId, String userNickname, String userImg) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userImg = userImg;
    }
}
