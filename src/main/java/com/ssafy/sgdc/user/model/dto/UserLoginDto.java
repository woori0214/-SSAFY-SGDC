package com.ssafy.sgdc.user.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    private String loginId;
    private String userPassword;

}
