package com.ssafy.sgdc.solo.dto.response;

import com.ssafy.sgdc.solo.dto.SoloDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class SoloListResponse {

    private int status;
    private String message;
    private List<SoloDto> solos;

}
