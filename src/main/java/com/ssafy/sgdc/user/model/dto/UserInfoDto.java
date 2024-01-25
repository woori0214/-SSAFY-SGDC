package com.ssafy.sgdc.user.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String loginId;
    private int userSsafyId;
    private String userEmail;
    private String userNickname;
    private String userName;
    private String userImg;
    private long badgeId;
    private String userPhone;
    private Boolean kakaoPush;
    private int challengeCnt;
    private int complainCnt;
}
