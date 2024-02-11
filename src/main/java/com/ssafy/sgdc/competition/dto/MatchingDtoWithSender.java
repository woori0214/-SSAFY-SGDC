package com.ssafy.sgdc.competition.dto;

import com.ssafy.sgdc.competition.domain.Matching;
import com.ssafy.sgdc.enums.CompetKind;
import com.ssafy.sgdc.enums.IsSender;
import com.ssafy.sgdc.enums.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchingDtoWithSender {

    private int matching_id;
    private int user_id;
    private String user_nickname;
    private int category_id;
    private CompetKind compet_kind;
    private IsSender is_sender;
    private LocalDateTime compet_expiration_time;
    private MatchStatus match_status;
    private String sender_nickname;


    public static MatchingDtoWithSender of(Matching matching, String sender_nickname) {
        return new MatchingDtoWithSender(
                matching.getMatchingId(),
                matching.getUser().getUserId(),
                matching.getUser().getUserNickname(),
                matching.getCategory().getCategoryId(),
                matching.getCompetKind(),
                matching.getIsSender(),
                matching.getCompetExpirationTime(),
                matching.getMatchStatus(),
                sender_nickname
        );
    }

}
