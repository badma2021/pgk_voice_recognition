package com.example.wereL.dao;

import com.example.wereL.model.dto.CategoryByTimeDTO;
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

    @Query(value="select to_char(e.created_at,'YYYY/FMMM')" +
            " AS date, sum(e.amount) as value from expense e" +
            " inner join expense_title t on e.expense_title_id=t.id " +
            "inner join category c on c.id=t.category_id where e.user_id= :userId " +
            "and c.id= :categoryId and (:expenseId is null or t.id = :expenseId) " +
            "group by date " +
            "order by date asc",
            nativeQuery=true)
    public List<CategoryByTimeDTO> findCategoryByTime(@Param("userId")Long userId,@Param("categoryId")Long categoryId,
                                                      @Param("expenseId")Long expenseId);

//    select date_trunc('year', created_at) AS year, date_part('month',e.created_at) AS month,
//    c.category_name,t.expense_name, sum(amount) from expense e
//    inner join expense_title t on t.id=e.expense_title_id
//    inner join category c on c.id=t.category_id where e.user_id=1
//    and t.category_id=2 and t.id=3 group by year,month, category_name, t.expense_name
//    order by year asc, month asc;

}
