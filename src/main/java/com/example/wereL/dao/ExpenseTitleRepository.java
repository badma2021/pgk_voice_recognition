package com.example.wereL.dao;

import com.example.wereL.model.entity.ExpenseTitle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseTitleRepository extends JpaRepository<ExpenseTitle, Long > {

    List<ExpenseTitle> findExpenseTitleByCategoryId(Long categoryId);
}
