package com.ssafy.sgdc.competition.dto.request;

import com.ssafy.sgdc.competition.domain.Matching;
import com.ssafy.sgdc.domain.entity.Category;
import com.ssafy.sgdc.enums.CompetKind;
import com.ssafy.sgdc.enums.IsSender;
import com.ssafy.sgdc.enums.MatchStatus;
import com.ssafy.sgdc.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateMatchingDto {

    private User user;
    private Category category;
    private CompetKind competKind;
    private IsSender isSender;
    private LocalDateTime competExpirationTime;
    private MatchStatus matchStatus;

    public static Matching from(CreateMatchingDto request){
        return new Matching(
                request.getUser(),
                request.getCategory(),
                request.getCompetKind(),
                request.getIsSender(),
                request.getCompetExpirationTime(),
                request.getMatchStatus()
        );
    }
}
