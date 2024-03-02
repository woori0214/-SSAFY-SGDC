package com.ssafy.sgdc.aspect;

import com.ssafy.sgdc.category.Category;
import com.ssafy.sgdc.category.dto.request.CreateUserCategoryDto;
import com.ssafy.sgdc.category.repository.CategoryRepo;
import com.ssafy.sgdc.category.repository.UserCategoryRepo;
import com.ssafy.sgdc.enums.CategoryStatus;
import com.ssafy.sgdc.user.User;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class UserSignUpAspect {

    private final UserCategoryRepo userCategoryRepo;
    private final CategoryRepo categoryRepo;

    // 회원가입하면 해당 회원 user_id로 UserCategory 6개 생성
    @AfterReturning(pointcut = "execution(* com.ssafy.sgdc.user.UserService.signUp(..))", returning = "user")
    public void afterUserSignUp(User user) {

        List<Category> categories = categoryRepo.findAll();

        for (Category category : categories) {
            CreateUserCategoryDto userCategory = new CreateUserCategoryDto(user, category, 0, 0, CategoryStatus.NONE_STATUS);
            userCategoryRepo.save(CreateUserCategoryDto.from(userCategory));
        }

    }

}
