package com.ssafy.sgdc.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private int userId;
    private String refreshToken;
}
