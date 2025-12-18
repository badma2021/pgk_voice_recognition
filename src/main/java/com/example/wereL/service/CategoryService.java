package com.example.wereL.service;

import com.example.wereL.dao.CategoryRepository;
import com.example.wereL.exception.CategoryNotFoundException;
import com.example.wereL.model.dto.CategoryDTO;
import com.example.wereL.model.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@CacheConfig(cacheNames = "cs")
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    @Resource
    private CategoryRepository categoryRepository;
    public List<Category> getCategories(Long userId) {
        logger.info("CategoryService.getCategories starts2");
        return categoryRepository.findByUserId(userId);
    }
    @Transactional
    public void updateCategory(CategoryDTO dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Invalid category data");
        }
        Category category = categoryRepository.findById(dto.getId())
                .orElseThrow(CategoryNotFoundException::new);
        category.setCategoryName(dto.getCategoryName());
        categoryRepository.save(category);

    }
}
