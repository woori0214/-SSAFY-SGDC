package com.ssafy.sgdc.competition.dto.response;

import com.ssafy.sgdc.competition.dto.CompetitionDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CompetitionResponse {

    private int status;
    private String message;
    private CompetitionDto competition;

}
