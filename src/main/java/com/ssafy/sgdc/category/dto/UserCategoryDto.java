package com.ssafy.sgdc.category.dto;

import com.ssafy.sgdc.category.UserCategory;
import com.ssafy.sgdc.enums.CategoryName;
import com.ssafy.sgdc.enums.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCategoryDto {

    private int userCategoryId;
    private int user_id;
    private int category_id;
    private CategoryName categoryName;
    private int categoryWinCnt;
    private int categoryFailCnt;
    private CategoryStatus categoryStatus;

    public static UserCategoryDto of(UserCategory userCategory) {
        return new UserCategoryDto(
                userCategory.getUserCategoryId(),
                userCategory.getUser().getUserId(),
                userCategory.getCategory().getCategoryId(),
                userCategory.getCategory().getCategoryName(),
                userCategory.getCategoryWinCnt(),
                userCategory.getCategoryFailCnt(),
                userCategory.getCategoryStatus()
        );
    }

}
