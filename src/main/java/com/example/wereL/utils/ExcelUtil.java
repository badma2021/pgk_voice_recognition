package com.example.wereL.utils;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
@Service
public class ExcelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    @SuppressWarnings("deprecation")
    public XSSFSheet readExcel(String path, String sheetName) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet(sheetName);
//        int rowNum = sheet.getPhysicalNumberOfRows();
//        int columNum = sheet.getRow(0).getPhysicalNumberOfCells();
        //Object[][] data = new Object[rowNum - 1][columNum];
//        for (int i = 1; i < rowNum; i++) {
//            for (int h = 0; h < columNum; h++) {
//                sheet.getRow(i).getCell(h).setCellType(CellType.STRING);
//                data[i - 1][h] = sheet.getRow(i).getCell(h).getStringCellValue();
//            }
//        }
        workbook.close();
      //  logger.info("Reading Excel file: the range consists from rows: "+rowNum+" and columns: " + columNum);
        return sheet;
    }

}