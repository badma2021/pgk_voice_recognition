package com.example.wereL.controller;

import com.example.wereL.model.dto.ExpenseDTO;
import com.example.wereL.model.dto.UserDTO;
import com.example.wereL.service.AuthService;
import com.example.wereL.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ExpenseController {
    private final ExpenseService expenseService;
    @PostMapping(value = "/store")
    public ResponseEntity<String> store(@RequestBody ExpenseDTO[] expenseDTO){
        return new ResponseEntity<>(expenseService.save(expenseDTO), HttpStatus.OK);
    }
}
