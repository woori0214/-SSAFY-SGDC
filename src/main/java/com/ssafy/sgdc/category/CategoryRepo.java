package com.ssafy.sgdc.category;

import com.ssafy.sgdc.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Category findByCategoryId(int categoryId);

}
