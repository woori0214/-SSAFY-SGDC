package com.ssafy.sgdc.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    private int userId;
    private String userImg;

}
