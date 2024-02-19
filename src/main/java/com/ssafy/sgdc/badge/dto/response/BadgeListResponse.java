package com.ssafy.sgdc.badge.dto.response;

import com.ssafy.sgdc.badge.dto.BadgeDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class BadgeListResponse {

    private int status;
    private String message;
    private List<BadgeDto> badges;

}
