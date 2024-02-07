package com.ssafy.sgdc.competition.dto;

import com.ssafy.sgdc.competition.domain.Competition;
import com.ssafy.sgdc.enums.CompetResult;
import com.ssafy.sgdc.enums.IsSender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionDto {

    private int compet_id;
    private int compet_result_id;
    private CompetResult compet_result;
    private int matching_id;
    private IsSender is_sender;
    private int other_id;
    private String other_nickname;
    private String user_image_auth;
    private String other_image_auth;
    private LocalDateTime done_at;

    public static CompetitionDto of(Competition competition, int matching_id, IsSender is_sender,
                                    int other_id, String other_nickname, String user_image_auth,
                                    String other_image_auth, LocalDateTime done_at) {
        return  new CompetitionDto(
                competition.getCompetId(),
                competition.getCompetDetail().getCompetResultId(),
                competition.getCompetDetail().getCompetResult(),
                matching_id,
                is_sender,
                other_id,
                other_nickname,
                user_image_auth,
                other_image_auth,
                done_at
        );
    }

}
