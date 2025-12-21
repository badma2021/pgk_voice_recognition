package com.example.wereL.service;

import com.example.wereL.dao.CategoryRepository;

import com.example.wereL.dao.ExpenseTitleRepository;
import com.example.wereL.exception.CategoryNotFoundException;

import com.example.wereL.model.dto.ExpenseTitleCreateDTO;
import com.example.wereL.model.dto.ExpenseTitleEditDTO;
import com.example.wereL.model.entity.Category;
import com.example.wereL.model.entity.ExpenseTitle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
public class ExpenseTitleService {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseTitleService.class);
    @Resource
    private CategoryRepository categoryRepository;
    @Resource
    private ExpenseTitleRepository expenseTitleRepository;

    @Transactional
    public void update(ExpenseTitleEditDTO dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("Invalid category data");
        }
        ExpenseTitle expenseTitle= expenseTitleRepository.findById(dto.getId())
                .orElseThrow(CategoryNotFoundException::new);
        expenseTitle.setExpenseName(dto.getExpenseName());
        expenseTitleRepository.save(expenseTitle);

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
    }
}

