package com.ssafy.sgdc.category.repository;

import com.ssafy.sgdc.category.UserCategory;
import com.ssafy.sgdc.enums.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCategoryRepo extends JpaRepository<UserCategory, Integer> {

    List<UserCategory> findUserCategoryByUserUserId(int userId);

    Optional<UserCategory> findUserCategoryByUserUserIdAndAndCategoryCategoryId(int userId, int categoryId);

    Optional<UserCategory> findByUserUserIdAndCategoryCategoryIdAndCategoryStatus(int userId, int categoryId, CategoryStatus categoryStatus);

}
