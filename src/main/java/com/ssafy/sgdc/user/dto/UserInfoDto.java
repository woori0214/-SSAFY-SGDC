package com.ssafy.sgdc.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private int userId;
    private int userSsafyId;
    private String userNickname;
    private String userImg;
    private long badgeId;
    private int challengeCnt;
    private int complainCnt;
}
