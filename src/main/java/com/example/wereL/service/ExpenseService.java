package com.example.wereL.service;

import com.example.wereL.dao.*;
import com.example.wereL.model.dto.*;
import com.example.wereL.model.entity.Category;
import com.example.wereL.model.entity.Expense;
import com.example.wereL.model.entity.ExpenseTitle;
import com.example.wereL.repository.ReportRepositoryImpl;
import com.example.wereL.utils.DtoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@CacheConfig(cacheNames = "es")
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

    DtoUtils dtoUtils = new DtoUtils();

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

    @CacheEvict(key = "#id")
    public void deleteById(Long id) {
        logger.info("ExpenseService.deleteById starts");
        expenseRepository.deleteById(id);
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Cacheable(value="categoryByTime")
    public Map<String, Object> getCategoryByTime(Long userId, Long categoryId, Long expenseId) {
        logger.info("ExpenseService.getCategoryByTime starts");
        List<CategoryByTimeDTO> list = expenseRepository.findCategoryByTime(userId, categoryId, expenseId);

        return dtoUtils.convertToCategoryReport(list);
    }


    public List<ExcelDTO> getExcel(Long userId, String startDate, String endDate) {
        logger.info("ExpenseService.getExcel starts");
        List<ExcelDTO> list = expenseRepository.exportToExcel(userId, startDate, endDate);
        logger.info("ExpenseService.getExcel before print");
        //list.forEach(System.out::println);

        return list;
    }
}
