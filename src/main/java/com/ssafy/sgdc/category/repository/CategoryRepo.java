package com.ssafy.sgdc.category.repository;

import com.ssafy.sgdc.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Category findByCategoryId(int categoryId);

}
