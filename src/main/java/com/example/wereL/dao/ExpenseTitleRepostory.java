package com.example.wereL.dao;

import com.example.wereL.model.entity.ExpenseTitle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTitleRepostory extends JpaRepository<ExpenseTitle, Long > {
}
