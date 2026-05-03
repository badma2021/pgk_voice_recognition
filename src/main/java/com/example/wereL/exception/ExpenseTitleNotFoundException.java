package com.example.wereL.exception;

public class ExpenseTitleNotFoundException extends RuntimeException{
    public ExpenseTitleNotFoundException() {
        super("ExpenseTitle not found");
    }

}
