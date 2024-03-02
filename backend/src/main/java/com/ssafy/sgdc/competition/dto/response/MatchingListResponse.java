package com.ssafy.sgdc.competition.dto.response;

import com.ssafy.sgdc.competition.dto.MatchingDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class MatchingListResponse {

    private int status;
    private String message;
    private List<MatchingDto> matching;

}
