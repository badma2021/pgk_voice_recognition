package com.example.wereL.repository;

import com.example.wereL.model.dto.ReportDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        String sql = "select date_part('year', e.created_at) AS year, date_part('month', e.created_at) AS month, c.category_name, sum(e.amount) from expense e" +
                " inner join expense_title t on e.expense_title_id=t.id " +
                "inner join category c on t.category_id=c.id " +
                "where e.user_id=? and date_part('year', e.created_at)=?::integer and date_part('month', e.created_at)=?::integer " +
                "group by year, month, c.category_name order by year asc, month asc, sum desc";
        List<ReportDTO> list = new ArrayList<ReportDTO>();
        try (
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.setString(2, year);
            ps.setString(3, month);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    ReportDTO record = new ReportDTO();
                    record.setYear(rs.getString("year"));
                    record.setMonth(rs.getString("month"));
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
}
