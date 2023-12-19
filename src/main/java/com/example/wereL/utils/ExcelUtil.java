package com.example.wereL.utils;


import com.example.wereL.controller.ExpenseController;
import com.example.wereL.model.dto.ExcelDTO;
import com.example.wereL.model.dto.ExpenseDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public void writeToOutputStream(HttpServletResponse response, XSSFWorkbook wb) {
        ServletOutputStream out;
        try {
            out = response.getOutputStream();
            wb.write(out);
            wb.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void createList(ExcelDTO record, Row row) // creating cells for each row
    {
        Cell cell = row.createCell(0);
        cell.setCellValue(record.getDate());

        cell = row.createCell(1);
        cell.setCellValue(record.getExpenseName());
        cell = row.createCell(2);
        cell.setCellValue(record.getValue());
        cell = row.createCell(3);
        cell.setCellValue(record.getComment());
        cell = row.createCell(4);
        cell.setCellValue(record.getCategoryName());
        cell = row.createCell(5);
        cell.setCellValue(record.getCurrency());
        cell = row.createCell(6);
        cell.setCellValue(record.getExchangeRateToRuble());

    }

    public void getWorkbook(List<ExcelDTO> list, HttpServletResponse response) {
        XSSFWorkbook wb = new XSSFWorkbook();
        try {

            XSSFSheet sheet = wb.createSheet("sheet1");// creating a blank sheet
            int rownum = 1;
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Date");
            header.createCell(1).setCellValue("Expense");
            header.createCell(2).setCellValue("Value");
            header.createCell(3).setCellValue("Comment");
            header.createCell(4).setCellValue("Category");
            header.createCell(5).setCellValue("Currency");
            header.createCell(6).setCellValue("Rate");
            for (ExcelDTO record : list) {
                Row row = sheet.createRow(rownum++);
                createList(record, row);

            }
            writeToOutputStream(response, wb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getEmptyExcelFileAsBytes(List<ExcelDTO> list) throws IOException {

//        try (Workbook workbook = new XSSFWorkbook()) {
//
//            workbook.createSheet("test"); // do some logic
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(list);
            return bos.toByteArray();
        }
    }

    public ExpenseDTO[] excelToExcelDTO(MultipartFile file, String userId) throws IOException {
        logger.info("excelToExcelDTO starts");

        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet worksheet = workbook.getSheetAt(0);
            int len = worksheet.getPhysicalNumberOfRows() - 1;
            ExpenseDTO[] productList = new ExpenseDTO[len-1];
            logger.info("len: {}", len);
            for (int index = 0; index < len; index++) {
                if (index > 0) {
                    logger.info("index: {}", index);
                    var product = new ExpenseDTO();

                    XSSFRow row = worksheet.getRow(index);
                    logger.info("createdAt: {}", row.getCell(0).getLocalDateTimeCellValue());
                    product.setCreatedAt(row.getCell(0).getLocalDateTimeCellValue());
                    product.setExpenseTitleId((int) row.getCell(1).getNumericCellValue());
                    product.setAmount(BigDecimal.valueOf(row.getCell(2).getNumericCellValue()));

                    if (row.getCell(3) != null) {
                        product.setComment(row.getCell(3).getStringCellValue());
                    } else {
                        product.setComment("");
                    }

                  //  product.setUserId((int) row.getCell(4).getNumericCellValue());
                    product.setUserId(Integer.parseInt(userId));
                 //   logger.info("getCell id: {}", (int) row.getCell(4).getNumericCellValue());
                    product.setCurrencyName(row.getCell(5).getStringCellValue());
                    product.setExchangeRateToRuble(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
                    logger.info("product: " + product);
                    productList[index-1] = product;
                }
            }

           // System.out.println(Arrays.toString(productList));
            return productList;
        }
    }

}





