package com.ssafy.sgdc.competition.dto.request;

import lombok.Getter;

@Getter
public class FriendSendRequest {

    private int userId;
    private int friendId;
    private int categoryId;

}
