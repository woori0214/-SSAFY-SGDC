package com.ssafy.sgdc.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Category findByCategoryId(int categoryId);

}
