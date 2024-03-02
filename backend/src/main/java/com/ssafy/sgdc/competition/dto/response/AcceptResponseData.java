package com.ssafy.sgdc.competition.dto.response;

import com.ssafy.sgdc.enums.CategoryName;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AcceptResponseData {

    private int matching_id;
    private int category_id;
    private CategoryName category_name;

}
