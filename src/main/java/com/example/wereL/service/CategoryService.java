package com.example.wereL.service;

import com.example.wereL.config.cache.CacheNames;
import com.example.wereL.dao.CategoryRepository;
import com.example.wereL.exception.CategoryNotFoundException;
import com.example.wereL.model.dto.CategoryDTO;
import com.example.wereL.model.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final JdbcTemplate jdbcTemplate;
    private final CategoryRepository categoryRepository;
    private final CacheManager cacheManager;

    public CategoryService(JdbcTemplate jdbcTemplate, CategoryRepository categoryRepository, CacheManager cacheManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryRepository = categoryRepository;
        this.cacheManager = cacheManager;
    }

    @Cacheable(cacheNames = CacheNames.CATEGORY_BY_USERID)
    public List<CategoryDTO> getCategories(Long userId) {
        logger.info("CategoryService.getCategories starts2");
       List<Category> categories= categoryRepository.findByUserId(userId);
        List<CategoryDTO> cats = categories.stream()
                .map(c -> new CategoryDTO(c.getId(), c.getCategoryName())).
                collect(Collectors.toList());
        return cats;
    }
    @Transactional
    public void updateCategory(CategoryDTO dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Invalid category data");
        }
        Category category = categoryRepository.findById(dto.getId())
                .orElseThrow(CategoryNotFoundException::new);
        category.setCategoryName(dto.getCategoryName());
        Long userId = category.getUser().getId();

        category.setCategoryName(dto.getCategoryName());
        categoryRepository.save(category);

        Objects.requireNonNull(cacheManager.getCache(CacheNames.CATEGORY_BY_USERID)).evict(userId);

    }

    @Transactional
    public void createCategory(CategoryDTO dto) {
        boolean exists = categoryRepository.findByCategoryNameAndUserId(dto.getCategoryName(), dto.getUserId()).isPresent();
        if (!exists) {
            try {
                jdbcTemplate.update("INSERT INTO category (category_name, user_id) VALUES (?, ?)",
                        dto.getCategoryName(), dto.getUserId());
                Objects.requireNonNull(cacheManager.getCache(CacheNames.CATEGORY_BY_USERID)).evict(dto.getUserId());
            } catch (DataIntegrityViolationException e) {
                // если причина — уникальность, просто игнорируем (или логируем)
            }
        }
    }
    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
        Long userId = category.getUser().getId();
        categoryRepository.deleteById(id);
        Objects.requireNonNull(cacheManager.getCache(CacheNames.CATEGORY_BY_USERID)).evict(userId);
    }
}
