package com.ssafy.sgdc.competition.dto.request;

import lombok.Getter;

@Getter
public class ImageAuthRequest {

    private int user_id;
    private int compet_id;
    private String auth_img;

}
