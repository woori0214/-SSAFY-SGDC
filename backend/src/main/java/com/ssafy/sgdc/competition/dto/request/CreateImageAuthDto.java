package com.ssafy.sgdc.competition.dto.request;

import com.ssafy.sgdc.competition.domain.Competition;
import com.ssafy.sgdc.competition.domain.ImageAuth;
import com.ssafy.sgdc.competition.domain.Matching;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateImageAuthDto {

    private String authImg;
    private LocalDateTime createAt;
    private Competition competition;
    private Matching matching;

    public static ImageAuth from(CreateImageAuthDto request) {
        return new ImageAuth(
                request.getAuthImg(),
                request.getCreateAt(),
                request.getCompetition(),
                request.getMatching()
        );
    }

}
