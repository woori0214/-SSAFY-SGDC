package com.ssafy.sgdc.competition.dto.request;

import com.ssafy.sgdc.competition.domain.CompetDetail;
import com.ssafy.sgdc.enums.CompetResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateCompetDetailDto {
    private CompetResult competResult;

    public static CompetDetail from(CreateCompetDetailDto request) {
        return new CompetDetail(
                request.getCompetResult()
        );
    }
}
