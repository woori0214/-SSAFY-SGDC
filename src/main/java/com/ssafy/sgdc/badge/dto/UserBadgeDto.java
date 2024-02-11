package com.ssafy.sgdc.badge.dto;

import com.ssafy.sgdc.badge.domain.Badge;
import com.ssafy.sgdc.badge.domain.UserBadge;
import com.ssafy.sgdc.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserBadgeDto {

    private Badge badge;
    private User user;

    public static UserBadge from(Badge badge, User user) {
        return new UserBadge(
                badge,
                user
        );
    }

}
