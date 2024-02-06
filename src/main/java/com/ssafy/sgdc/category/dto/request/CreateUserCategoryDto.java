package com.ssafy.sgdc.category.dto.request;

import com.ssafy.sgdc.category.Category;
import com.ssafy.sgdc.category.UserCategory;
import com.ssafy.sgdc.enums.CategoryStatus;
import com.ssafy.sgdc.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateUserCategoryDto {

    private User user;
    private Category category;
    private int categoryWinCnt;
    private int categoryFailCnt;
    private CategoryStatus categoryStatus;

    public static UserCategory from(CreateUserCategoryDto request) {
        return new UserCategory(
                request.getUser(),
                request.getCategory(),
                request.getCategoryWinCnt(),
                request.getCategoryFailCnt(),
                request.getCategoryStatus()
        );
    }

}
