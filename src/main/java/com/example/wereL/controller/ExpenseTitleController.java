package com.example.wereL.controller;


import com.example.wereL.model.dto.ExpenseTitleCreateDTO;
import com.example.wereL.model.dto.ExpenseTitleEditDTO;
import com.example.wereL.service.ExpenseTitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expenseTitle")
public class ExpenseTitleController {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseTitleController.class);
    private final ExpenseTitleService expenseTitleService;


    public ExpenseTitleController(ExpenseTitleService expenseTitleService) {
        this.expenseTitleService = expenseTitleService;

    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateExpenseTitle(@RequestBody ExpenseTitleEditDTO dto){
        expenseTitleService.update(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createExpenseTitle(@RequestBody ExpenseTitleCreateDTO dto){
        expenseTitleService.create(dto);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteExpenseTitle(@PathVariable Long id){
        expenseTitleService.delete(id);
        return ResponseEntity.ok().build();

    }

}
