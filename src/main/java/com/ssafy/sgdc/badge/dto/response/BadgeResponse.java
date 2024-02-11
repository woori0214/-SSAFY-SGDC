package com.ssafy.sgdc.badge.dto.response;

import com.ssafy.sgdc.badge.dto.BadgeDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BadgeResponse {

    private int status;
    private String message;
    private BadgeDto badge;

}
