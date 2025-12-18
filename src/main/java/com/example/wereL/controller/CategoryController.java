package com.example.wereL.controller;

import com.example.wereL.model.dto.CategoryDTO;
import com.example.wereL.model.entity.Category;
import com.example.wereL.service.CategoryService;
import com.example.wereL.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    public CategoryController(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }


    @GetMapping("/category/{userId}")
    public ResponseEntity<List<CategoryDTO>> getCategories(@PathVariable Long userId) {
        List<Category> categories = categoryService.getCategories(userId);
        List<CategoryDTO> cats = categories.stream()
                .map(c -> new CategoryDTO(c.getId(), c.getCategoryName())).
                collect(Collectors.toList());

        return new ResponseEntity<>(cats, HttpStatus.OK);
    }

    @PutMapping("/category/update")
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryDTO dto){
        categoryService.updateCategory(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/category/create")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryDTO dto){
        categoryService.createCategory(dto);
        return ResponseEntity.ok().build();

}}
