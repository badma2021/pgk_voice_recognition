package com.example.pgk.utils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.example.pgk.model.dto.PartDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class GenerateExcelReport {

    public static ByteArrayInputStream usersToExcel(PartDTO[] partdtos) throws IOException {
        String[] COLUMNs = { "id", "partName", "partNumber", "productionYear", "factoryNumber", "comment",
                "audioRecordName","createdAt","userId" };
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet("Parts");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Header Row
            Row headerRow = sheet.createRow(0);

            // Table Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (PartDTO partdto : partdtos) {
                Row row = sheet.createRow(rowIdx++);

                //row.createCell(0).setCellValue(partdto.getId().toString());
                row.createCell(1).setCellValue(partdto.getPartName().toString());
                row.createCell(2).setCellValue(partdto.getPartNumber().toString());
                row.createCell(3).setCellValue(partdto.getProductionYear().toString());
                row.createCell(4).setCellValue(partdto.getFactoryNumber().toString());
                row.createCell(5).setCellValue(partdto.getComment().toString());
                row.createCell(6).setCellValue(partdto.getAudioRecordName().toString());
                row.createCell(7).setCellValue(partdto.getCreatedAt().toString());
                row.createCell(8).setCellValue(partdto.getUserId().toString());

            }

            //Auto-size all the above columns
           // sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            sheet.autoSizeColumn(8);

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}