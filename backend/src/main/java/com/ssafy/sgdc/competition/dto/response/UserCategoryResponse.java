package com.ssafy.sgdc.competition.dto.response;

import com.ssafy.sgdc.category.dto.UserCategoryDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UserCategoryResponse {

    private int status;
    private String message;
    private UserCategoryDto user_category;

}
