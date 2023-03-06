package com.example.wereL.service;

import com.example.wereL.dao.CategoryRepository;
import com.example.wereL.dao.ExpenseRepository;
import com.example.wereL.dao.ExpenseTitleRepository;
import com.example.wereL.dao.UserRepository;
import com.example.wereL.model.dto.ExpenseDTO;
import com.example.wereL.model.dto.ReportDTO;
import com.example.wereL.model.entity.Category;
import com.example.wereL.model.entity.Expense;
import com.example.wereL.model.entity.ExpenseTitle;
import com.example.wereL.repository.ReportRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ExpenseService {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);
    @Resource
    private ExpenseRepository expenseRepository;
    @Resource
    private ExpenseTitleRepository expenseTitleRepository;
    @Resource
    private CategoryRepository categoryRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private ReportRepositoryImpl reportRepository;

    public String save(ExpenseDTO[] expenseDTO) {
        logger.info("ExpenseService.save starts");
        List<Expense> exps = IntStream
                .rangeClosed(0, expenseDTO.length - 1)
                .mapToObj(j -> new Expense(
                        LocalDateTime.now(),
                        expenseTitleRepository.getById(Long.valueOf(expenseDTO[j].getExpenseTitleId())),
                        expenseDTO[j].getAmount().multiply(expenseDTO[j].getExchangeRateToRuble()),
                        expenseDTO[j].getComment(),
                        userRepository.getById(Long.valueOf(expenseDTO[j].getUserId())),
                        expenseDTO[j].getCurrencyName(),
                        expenseDTO[j].getExchangeRateToRuble()
                ))
                .collect(Collectors.toList());
        logger.info("before saveAll");
        expenseRepository.saveAllAndFlush(exps);
        return "запись учтена";
    }

    public List<Category> getCategories() {
        logger.info("ExpenseService.getCategories starts");
        return categoryRepository.findAll();
    }

    public List<ExpenseTitle> getExpenseTitleByCategory(Long categoryId) {
        logger.info("ExpenseService.getExpenseTitleByCategory starts");
        return expenseTitleRepository.findExpenseTitleByCategoryId(categoryId);
    }

    public List<ReportDTO> getReportByDate(Long userId, String year, String month) {
        logger.info("ExpenseService.getReportByDate starts");
        return reportRepository.findByDateAndUser(userId, year, month);
    }

}
