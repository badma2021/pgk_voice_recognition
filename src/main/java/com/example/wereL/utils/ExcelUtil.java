package com.example.wereL.utils;


import com.example.wereL.model.dto.ExcelDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

@Service
public class ExcelUtil {
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

}




