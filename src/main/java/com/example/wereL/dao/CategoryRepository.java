package com.example.wereL.dao;

import com.example.wereL.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  CategoryRepository extends JpaRepository<Category, Long > {
    List<Category> findByUserId(Long userId);

    Optional<Category> findByCategoryNameAndUserId(String categoryName, Long userId);
}
