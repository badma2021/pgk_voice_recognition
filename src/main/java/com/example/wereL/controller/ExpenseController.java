package com.example.wereL.controller;

import com.example.wereL.model.dto.ExpenseDTO;
import com.example.wereL.model.dto.HistoryDTO;
import com.example.wereL.model.dto.ReportDTO;
import com.example.wereL.model.entity.Category;
import com.example.wereL.model.entity.ExpenseTitle;
import com.example.wereL.service.ExpenseService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ExpenseController {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping(value = "/store")
    public ResponseEntity<String> store(@RequestBody ExpenseDTO[] expenseDTO) {
        logger.info("ExpenseController.store starts");
        return new ResponseEntity<>(expenseService.save(expenseDTO), HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = expenseService.getCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //http://127.0.0.1:8888/api/v1/selected_expensetitle?id=2
    @GetMapping("/selected_expensetitle")
    public ResponseEntity<List<ExpenseTitle>> getExpenseTitleByCategory(@RequestParam(name = "id") Long categoryId) {
        List<ExpenseTitle> expenseTitles = expenseService.getExpenseTitleByCategory(categoryId);

        return new ResponseEntity<>(expenseTitles, HttpStatus.OK);
    }

    @PostMapping(value = "/report")
    public ResponseEntity<List<ReportDTO>> reportByDate(@RequestBody String feedInput) {
        logger.info("ExpenseController.report starts");
        JSONObject jsonObject = new JSONObject(feedInput);
        String year = jsonObject.getString("year");
        String month = jsonObject.getString("month");
        Long userId = Long.valueOf(jsonObject.getString("userId"));
        return new ResponseEntity<>(expenseService.getReportByDate(userId, year, month), HttpStatus.OK);
    }
    @PostMapping(value = "/history")
    public ResponseEntity<List<HistoryDTO>> getHistory(@RequestBody String feedInput) {
        logger.info("ExpenseController.getHistory starts");
        JSONObject jsonObject = new JSONObject(feedInput);
        String startDate = jsonObject.getString("startDate");
        String endDate = jsonObject.getString("endDate");
        Long userId = Long.valueOf(jsonObject.getString("userId"));
        return new ResponseEntity<>(expenseService.getDataByDateRange(userId, startDate, endDate), HttpStatus.OK);
    }

}
