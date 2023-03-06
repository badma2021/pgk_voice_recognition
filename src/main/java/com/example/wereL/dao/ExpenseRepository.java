package com.example.wereL.dao;

import com.example.wereL.model.dto.ReportDTO;
import com.example.wereL.model.entity.Expense;
import com.example.wereL.model.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long > {

//    @Query(value="select :year AS year, :month AS month, c.category_name, sum(e.amount) " +
//            "as sum from expense e " +
//            "inner join expense_title t on e.expense_title_id=t.id inner join category c on " +
//            "t.category_id=c.id " +
//            "group by year, month, c.category_name order by year asc, month asc, sum desc", nativeQuery = true)
//    List<ReportDTO> findByDateAndUser(@Param("year") String year, @Param("month") String month);

//    @Query(value="select extract(YEAR from e.created_at) AS year, extract(MONTH from e.created_at) AS month, c.category_name, sum(e.amount) " +
//            "as sum from expense e " +
//            "inner join expense_title t on e.expense_title_id=t.id inner join category c on " +
//            "t.category_id=c.id " +
//            "where extract(YEAR from e.created_at)= :year and extract(MONTH from e.created_at)= :month " +
//            "group by year, month, c.category_name order by year asc, month asc, sum desc", nativeQuery = true)
//    List<ReportDTO> findByDateAndUser2(String year, String month);

//    @Query(value="select extract(YEAR from e.created_at) AS year, extract(MONTH from e.created_at) AS month, c.category_name, sum(e.amount) " +
//            "as sum from expense e " +
//            "inner join expense_title t on e.expense_title_id=t.id inner join category c on " +
//            "t.category_id=c.id " +
//            "where e.user_id='1' and extract(YEAR from e.created_at)='2023' and extract(MONTH from e.created_at)='1' " +
//            "group by year, month, c.category_name order by year asc, month asc, sum desc", nativeQuery = true)
//    List<ReportDTO> findByDateAndUser();
}
