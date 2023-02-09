package com.example.wereL.controller;

import com.example.wereL.model.dto.ExpenseDTO;
import com.example.wereL.model.entity.Category;
import com.example.wereL.model.entity.ExpenseTitle;
import com.example.wereL.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ExpenseController {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping(value = "/store")
    public ResponseEntity<String> store(@RequestBody ExpenseDTO[] expenseDTO){
        logger.info("ExpenseController.store starts");
        return new ResponseEntity<>(expenseService.save(expenseDTO), HttpStatus.OK);
    }
    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = expenseService.getCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //http://127.0.0.1:8888/api/v1/selected_expensetitle?id=2
    @GetMapping("/selected_expensetitle")
    public ResponseEntity<List<ExpenseTitle>> getExpenseTitleByCategory(@RequestParam(name = "id") Long categoryId) {
        List<ExpenseTitle> expenseTitles = expenseService.getExpenseTitleByCategory(categoryId);

        return new ResponseEntity<>(expenseTitles, HttpStatus.OK);
    }
}
