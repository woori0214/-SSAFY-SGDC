package com.ssafy.sgdc.competition.dto.response;

import com.ssafy.sgdc.competition.dto.request.CompetitionProgressDetailDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ProgressCompetitionDetailResponse {

    private int status;
    private String message;
    private List<CompetitionProgressDetailDto> competitions;

}
