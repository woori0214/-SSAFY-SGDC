package com.ssafy.sgdc.follow.dto;

import lombok.Data;


@Data
public class FollowCountDto {

    private int followerCount;
    private int followingCount;

    public FollowCountDto(int followerCount, int followingCount) {
        this.followerCount=followerCount;
        this.followingCount=followingCount;
    }
}
