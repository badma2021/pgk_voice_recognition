package com.example.wereL.controller;

import com.example.wereL.exception.UserNotFoundException;
import com.example.wereL.model.dto.*;
import com.example.wereL.model.entity.Category;
import com.example.wereL.model.entity.ExpenseTitle;
import com.example.wereL.service.ExpenseService;
import com.example.wereL.utils.ExcelUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ExpenseController {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);
    private final ExpenseService expenseService;
    private final ExcelUtil excelUtil;

    public ExpenseController(ExpenseService expenseService, ExcelUtil excelUtil) {
        this.expenseService = expenseService;
        this.excelUtil = excelUtil;
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
    public ResponseEntity<Page<List<HistoryDTO>>> getHistory(@RequestBody String feedInput) {
        logger.info("ExpenseController.getHistory starts");

        JSONObject jsonObject = new JSONObject(feedInput);
        String startDate = jsonObject.getString("startDate");
        String endDate = jsonObject.getString("endDate");
        Long userId = Long.valueOf(jsonObject.getString("userId"));
        int page = Integer.parseInt(jsonObject.getString("page"));
        int size = Integer.parseInt(jsonObject.getString("size"));
        Pageable paging = PageRequest.of(page, size);
        return new ResponseEntity<>(expenseService.getDataByDateRange(userId, startDate, endDate, paging), HttpStatus.OK);
    }

    @GetMapping("/history/lastFive")
    public ResponseEntity<List<HistoryDTO>> getLastFive(@RequestParam(name = "id") Long userId) {
        logger.info("ExpenseController.getLastFive starts");
        int page = 0;
        int size = 5;
        //   Pageable paging = PageRequest.of(page, size);
        return new ResponseEntity<>(expenseService.getLastFive(userId), HttpStatus.OK);
    }

    @DeleteMapping("/history/delete{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable Long id) {
        logger.info("ExpenseController.deleteProduct starts");
        return expenseService.findById(id).map(p -> {
            expenseService.deleteById(id);
            return ResponseEntity.ok().body((true));
        }).orElseThrow(() -> new UserNotFoundException());
    }

    @PostMapping(value = "/categoryByTime")
    public ResponseEntity<Map<String, Object>> categoryByTime(@RequestBody String feedInput) {
        logger.info("ExpenseController.categoryByTime starts");
        JSONObject jsonObject = new JSONObject(feedInput);
        Long categoryId = jsonObject.getLong("categoryId");
        Long expenseId = jsonObject.getLong("expenseId");
        if (expenseId == 0L) {
            expenseId = null;
        }

        Long userId = Long.valueOf(jsonObject.getString("userId"));

        return new ResponseEntity<>(expenseService.getCategoryByTime(userId, categoryId, expenseId), HttpStatus.OK);
    }

    @PostMapping(value = "/export")
    public ResponseEntity exportToExcel(@RequestBody String feedInput, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject(feedInput);
        String startDate = jsonObject.getString("startDate");
        String endDate = jsonObject.getString("endDate");
        Long userId = Long.valueOf(jsonObject.getString("userId"));
        response.setHeader("Content-Disposition", "attachment; filename=\"myFileName.xlsx\"");

        List<ExcelDTO> list = expenseService.getExcel(userId, startDate, endDate);
        excelUtil.getWorkbook(list, response);

        try {
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().build();
    }

//    @PostMapping(value = "/export")
//    public byte[] exportToExcel(@RequestBody String feedInput) {
//        logger.info("ExpenseController.exportToExcel starts");
//        JSONObject jsonObject = new JSONObject(feedInput);
//        String startDate = jsonObject.getString("startDate");
//        String endDate = jsonObject.getString("endDate");
//        Long userId = Long.valueOf(jsonObject.getString("userId"));
//        // response.setHeader("Content-Disposition", "attachment; filename=\"myFileName.xlsx\"");
//
//      List<ExcelDTO> list = expenseService.getExcel(userId, startDate, endDate);
//
//        try {
//            //byte[] output = excelUtil.getEmptyExcelFileAsBytes(list);
//            byte[] output = new ObjectMapper().writeValueAsBytes(list);
//            // byte[] output = new byte[5];
//            //  excelUtil.getWorkbook(list, response);
////            HttpHeaders responseHeaders = new HttpHeaders();
////            responseHeaders.set("charset", "utf-8");
////            // responseHeaders.setContentType(MediaType.valueOf("text/html"));
////            responseHeaders.setContentLength(output.length);
////            responseHeaders.set("Content-disposition", "attachment; filename=\"myFileName.xlsx\"");
//
//            return output;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


}
