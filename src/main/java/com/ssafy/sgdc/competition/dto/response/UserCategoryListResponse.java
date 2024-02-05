package com.ssafy.sgdc.competition.dto.response;

import com.ssafy.sgdc.category.dto.UserCategoryDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class UserCategoryListResponse {

    private int status;
    private String message;
    private List<UserCategoryDto> user_categories;

}
