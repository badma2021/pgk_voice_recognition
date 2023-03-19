package com.example.wereL.dao;

import com.example.wereL.model.dto.HistoryDTO;
import com.example.wereL.model.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long > {


    @Query(value="select e.id, date(e.created_at), t.expense_name as expenseName, e.amount as value from expense e" +
            " inner join expense_title t on e.expense_title_id=t.id where e.user_id= :userId and date(e.created_at) " +
            "between cast(:startDate AS date) and cast(:endDate AS date) order by e.id desc",
            nativeQuery=true)
    public Page<List<HistoryDTO>> findByDateRange(@Param("userId")Long userId,@Param("startDate") String startDate,@Param("endDate") String endDate, Pageable pageable);

    @Query(value="select e.id, date(e.created_at), t.expense_name as expenseName, e.amount as value from expense e" +
            " inner join expense_title t on e.expense_title_id=t.id where e.user_id= :userId order by e.id desc limit 5",
            nativeQuery=true)
    public List<HistoryDTO> findLastFive(@Param("userId")Long userId);
}
