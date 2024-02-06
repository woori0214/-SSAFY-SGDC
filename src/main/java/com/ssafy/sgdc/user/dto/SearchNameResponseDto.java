package com.ssafy.sgdc.user.dto;

import lombok.Data;

@Data
public class SearchNameResponseDto {
    private int userId;
    private String userNickname;

    public SearchNameResponseDto(int userId, String userNickname) {
        this.userId = userId;
        this.userNickname = userNickname;
    }
}
