package com.ssafy.sgdc.badge.dto;

import com.ssafy.sgdc.badge.domain.Badge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeDto {

    private int badge_id;
    private String badge_name;
    private String badge_detail;
    private String badge_img;

    public static BadgeDto of (Badge badge) {
        return  new BadgeDto(
                badge.getBadgeId(),
                badge.getBadgeName(),
                badge.getBadgeDetail(),
                badge.getBadgeImg()
        );
    }

}
