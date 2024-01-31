package com.ssafy.sgdc.follow.dto;

import lombok.Data;


@Data
public class FollowCountDto {

    private Long followerCount;
    private Long followingCount;

    public FollowCountDto(Long followerCount, Long followingCount) {
        this.followerCount=followerCount;
        this.followingCount=followingCount;
    }
}
