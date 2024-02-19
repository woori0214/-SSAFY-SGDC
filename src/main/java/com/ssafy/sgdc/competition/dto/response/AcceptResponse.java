package com.ssafy.sgdc.competition.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AcceptResponse {

    private int status;
    private String message;
    private AcceptResponseData matching;

}
