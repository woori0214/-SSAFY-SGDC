package com.ssafy.sgdc.solo.dto;

import com.ssafy.sgdc.enums.SoloResult;
import com.ssafy.sgdc.enums.SoloStatus;
import com.ssafy.sgdc.solo.domain.Solo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SoloDto {

    private SoloStatus solo_status;
    private SoloResult solo_result;
    private int category_id;

    public static SoloDto of(Solo solo) {
        return new SoloDto(
                solo.getSoloStatus(),
                solo.getSoloResult(),
                solo.getCategory().getCategoryId()
        );
    }

}
