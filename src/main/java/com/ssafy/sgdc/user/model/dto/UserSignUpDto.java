package com.ssafy.sgdc.user.model.dto;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class UserSignUpDto {
    private String loginId;
    private String userEmail;
    private int userSsafyId;
    private String userNickname;
    private String userName;
    private String userPassword;
    private String userImg;
    private String userPhone;
    private Boolean kakaoPush;
}