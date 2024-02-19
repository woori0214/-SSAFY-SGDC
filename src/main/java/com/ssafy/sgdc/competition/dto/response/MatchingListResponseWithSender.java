package com.ssafy.sgdc.competition.dto.response;

import com.ssafy.sgdc.competition.dto.MatchingDtoWithSender;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class MatchingListResponseWithSender {

    private int status;
    private String message;
    private List<MatchingDtoWithSender> matching;

}
