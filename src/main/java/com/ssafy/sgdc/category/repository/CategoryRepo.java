package com.ssafy.sgdc.category.repository;

import com.ssafy.sgdc.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    Optional<Category> findByCategoryId(int categoryId);

}
