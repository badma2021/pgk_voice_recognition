package com.example.wereL.dao;

import com.example.wereL.model.entity.Expense;
import com.example.wereL.model.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long > {
}
