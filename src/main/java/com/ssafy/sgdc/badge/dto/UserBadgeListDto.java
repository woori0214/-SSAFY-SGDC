package com.ssafy.sgdc.badge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserBadgeListDto {
    private int userId;
    private int badgeId;
}
