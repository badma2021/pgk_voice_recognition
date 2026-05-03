package com.example.wereL.service;

import com.example.wereL.config.cache.CacheNames;
import com.example.wereL.dao.CategoryRepository;

import com.example.wereL.dao.ExpenseTitleRepository;
import com.example.wereL.exception.*;

import com.example.wereL.model.dto.ExpenseTitleCreateDTO;
import com.example.wereL.model.dto.ExpenseTitleEditDTO;
import com.example.wereL.model.entity.Category;
import com.example.wereL.model.entity.ExpenseTitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ExpenseTitleService {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseTitleService.class);

    private final CategoryRepository categoryRepository;

    private final ExpenseTitleRepository expenseTitleRepository;
    private final CacheManager cacheManager;

    public ExpenseTitleService(CategoryRepository categoryRepository, ExpenseTitleRepository expenseTitleRepository, CacheManager cacheManager) {
        this.categoryRepository = categoryRepository;
        this.expenseTitleRepository = expenseTitleRepository;
        this.cacheManager = cacheManager;
    }


    @Transactional
    public void update(ExpenseTitleEditDTO dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Invalid category data");
        }
        ExpenseTitle expenseTitle= expenseTitleRepository.findById(dto.getId())
                .orElseThrow(CategoryNotFoundException::new);
        expenseTitle.setExpenseName(dto.getExpenseName());
        expenseTitleRepository.save(expenseTitle);

        Objects.requireNonNull(cacheManager.getCache(CacheNames.EXPENSE_BY_CATEGORY)).evict(expenseTitle.getCategory().getId());

    }

    @Transactional
    public void create(ExpenseTitleCreateDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Invalid category data");
        }
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
        boolean exists = expenseTitleRepository.findByExpenseNameAndCategoryId(dto.getExpenseName(),
                dto.getCategoryId()).isPresent();
        System.out.println(exists);
        if (!exists) {
            expenseTitleRepository.save(ExpenseTitle.builder().
                    expenseName(dto.getExpenseName()).
                    category(category).build());

        }
        Objects.requireNonNull(cacheManager.getCache(CacheNames.EXPENSE_BY_CATEGORY)).evict(category.getId());
    }

    @Transactional
    public void delete(Long id) {
        ExpenseTitle exp=expenseTitleRepository.findById(id).orElseThrow(ExpenseTitleNotFoundException::new);

        expenseTitleRepository.deleteById(id);
        Objects.requireNonNull(cacheManager.getCache(CacheNames.EXPENSE_BY_CATEGORY)).evict(exp.getCategory().getId());
    }
}

