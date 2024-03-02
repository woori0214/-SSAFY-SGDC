package com.ssafy.sgdc.competition.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SendResponse {

    private int status;
    private String message;
    private SendResponseData matching;

}
