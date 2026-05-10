package com.example.wereL.service;

import com.example.wereL.config.cache.CacheNames;
import com.example.wereL.dao.*;
import com.example.wereL.model.dto.*;
import com.example.wereL.model.entity.Category;
import com.example.wereL.model.entity.Expense;
import com.example.wereL.model.entity.ExpenseTitle;
import com.example.wereL.repository.ReportRepositoryImpl;
import com.example.wereL.utils.DtoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ExpenseService {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    private final ExpenseRepository expenseRepository;

    private final ExpenseTitleRepository expenseTitleRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final ReportRepositoryImpl reportRepository;

    DtoUtils dtoUtils = new DtoUtils();

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseTitleRepository expenseTitleRepository,
                          CategoryRepository categoryRepository, UserRepository userRepository, ReportRepositoryImpl reportRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseTitleRepository = expenseTitleRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.reportRepository = reportRepository;

    }

    public String saveArray(ExpenseDTO[] expenseDTO) {
        logger.info("saveArray starts");
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

    public List<Category> getCategories(Long userId) {
        logger.info("ExpenseService.getCategories starts2");
        return categoryRepository.findByUserId(userId);
    }
    @Cacheable(cacheNames = CacheNames.EXPENSE_BY_CATEGORY)
    public ExpenseTitleDTO[] getExpenseTitleByCategory(Long categoryId) {
        logger.info("ExpenseService.getExpenseTitleByCategory starts");
        List<ExpenseTitle> expenseTitles = expenseTitleRepository.findExpenseTitleByCategoryId(categoryId);
        return expenseTitles.stream().map(e -> {
            final ExpenseTitleDTO expenseTitleDTO = dtoUtils.expenseTitleToExpenseTitleDTO(e);
            return expenseTitleDTO;
        }).toArray(ExpenseTitleDTO[]::new);
    }

    public List<ReportDTO> getReportByDate(Long userId, String year, String month) {
        logger.info("ExpenseService.getReportByDate starts");
        return reportRepository.findByDateAndUser(userId, year, month);
    }

    public Page<List<HistoryDTO>> getDataByDateRange(Long userId, String startDate, String endDate, Pageable pageable) {
        logger.info("ExpenseService.getDataByDateRange starts");
        return expenseRepository.findByDateRange(userId, startDate.toUpperCase(), endDate, pageable);
    }

    public List<HistoryDTO> getLastFive(Long userId) {
        logger.info("ExpenseService.getlastFive starts");
        return expenseRepository.findLastFive(userId);
    }

    public void deleteById(Long id) {
        logger.info("ExpenseService.deleteById starts");
        expenseRepository.deleteById(id);
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Cacheable(cacheNames = CacheNames.CATEGORY_BY_TIME)
    public Map<String, Object> getCategoryByTime(Long userId, Long categoryId, Long expenseId) {
        logger.info("ExpenseService.getCategoryByTime starts");
        List<CategoryByTimeDTO> list = expenseRepository.findCategoryByTime(userId, categoryId, expenseId);

        return dtoUtils.convertToCategoryReport(list);
    }

    public List<ExcelDTO> getExcel(Long userId, String startDate, String endDate) {
        logger.info("ExpenseService.getExcel starts");
        List<ExcelDTO> list = expenseRepository.exportToExcel(userId, startDate, endDate);
        logger.info("new ExpenseService.getExcel before print");
       // list.forEach(System.out::println);

        return list;
    }
    public String saveArrayOldDate(ExpenseDTO[] expenseDTO) {
        logger.info("saveArrayOldDate starts");
        List<Expense> exps = IntStream
                .rangeClosed(0, expenseDTO.length - 1)
                .mapToObj(j -> new Expense(
                        expenseDTO[j].getCreatedAt(),
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
}
