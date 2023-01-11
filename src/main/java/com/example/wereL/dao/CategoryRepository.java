package com.example.wereL.dao;

import com.example.wereL.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  CategoryRepository extends JpaRepository<Category, Long > {
}
