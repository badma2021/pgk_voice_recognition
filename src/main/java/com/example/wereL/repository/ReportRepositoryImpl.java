package com.example.wereL.repository;

import com.example.wereL.model.dto.HistoryDTO;
import com.example.wereL.model.dto.ReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportRepositoryImpl {
    private static final Logger logger = LoggerFactory.getLogger(ReportRepositoryImpl.class);
    String connectionURL = "jdbc:postgresql://postgres_db:5432/wereL";
    Connection con = DriverManager.getConnection(connectionURL, "root", "root");

    public ReportRepositoryImpl() throws SQLException {
    }

    @Transactional(readOnly = true)
    public List<ReportDTO> findByDateAndUser(Long userId, String year, String month) {
        logger.info(userId + " " + year + " " + month);
        String sql = "select c.category_name, sum(e.amount) from expense e" +
                " inner join expense_title t on e.expense_title_id=t.id " +
                "inner join category c on t.category_id=c.id " +
                "where e.user_id=? and date_part('year', e.created_at)=?::integer and date_part('month', e.created_at)=?::integer and c.category_name<>'earnings' " +
                "group by date_part('year', e.created_at), date_part('month', e.created_at), c.category_name order by date_part('year', e.created_at) asc, date_part('month', e.created_at) asc, sum desc";
        List<ReportDTO> list = new ArrayList<ReportDTO>();
        try (
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setString(2, year);
            ps.setString(3, month);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    ReportDTO record = new ReportDTO();
                    record.setCategory(rs.getString("category_name"));
                    record.setValue(rs.getDouble("sum"));
                    list.add(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info("start list PRINT");
        list.forEach(System.out::println);
        return list;
    }
//    @Transactional(readOnly = true)
//    public Page<List<HistoryDTO>> findByDateRange(Long userId, String startDate, String endDate, Pageable pageable) {
//
//        logger.info(userId + " " + startDate + " " + endDate + " " + pageable);
//
//        String sql = "select date(e.created_at), t.expense_name, e.amount from expense e " +
//                "inner join expense_title t on e.expense_title_id=t.id " +
//                "where e.user_id=? " +
//                "and date(e.created_at) between ?::date and ?::date order by e.id desc offset "+pageable.getPageNumber()*pageable.getPageSize()+" limit "+pageable.getPageSize()+";";
//        List<HistoryDTO> list = new ArrayList<HistoryDTO>();
//        try (
//
//
//                PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setLong(1, userId);
//            ps.setString(2, startDate);
//            ps.setString(3, endDate);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//
//                    HistoryDTO record = new HistoryDTO();
//                    record.setDate(rs.getString("date"));
//                    record.setExpenseName(rs.getString("expense_name"));
//                    record.setValue(rs.getDouble("amount"));
//                    list.add(record);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        logger.info("start list PRINT");
//        list.forEach(System.out::println);
//        Page page=new PageImpl<>(list);
//
//        return page;
    }


         //"and date(e.created_at) between ?::date and ?::date order by e.id desc offset "+pageable.getPageNumber()*pageable.getPageSize()+" limit "+pageable.getPageSize()+";";


