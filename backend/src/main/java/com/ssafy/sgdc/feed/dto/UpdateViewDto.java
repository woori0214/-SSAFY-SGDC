package com.ssafy.sgdc.feed.dto;


import lombok.Data;

@Data
public class UpdateViewDto {
    private int views;

    public UpdateViewDto (int views) {
        this.views=views;
    }

}
