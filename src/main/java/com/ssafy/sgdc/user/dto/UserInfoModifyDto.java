package com.ssafy.sgdc.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoModifyDto {
    private int userId;
    private String userNickname;
    private int badgeId;
    private String userPhone;
}
