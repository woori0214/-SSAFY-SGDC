package com.ssafy.sgdc.user;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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

    public UserSignUpDto() {
    }

    public UserSignUpDto(String loginId, String userEmail, int userSsafyId, String userNickname, String userName, String userPassword, String userImg, String userPhone, Boolean kakaoPush) {
        this.loginId = loginId;
        this.userEmail = userEmail;
        this.userSsafyId = userSsafyId;
        this.userNickname = userNickname;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userImg = userImg;
        this.userPhone = userPhone;
        this.kakaoPush = kakaoPush;
    }


}