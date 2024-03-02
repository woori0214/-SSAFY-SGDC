package com.ssafy.sgdc.competition.dto.response;

import com.ssafy.sgdc.competition.dto.CompetitionDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class CompetitionListResponse {

    private int status;
    private String message;
    private List<CompetitionDto> competitions;

}
