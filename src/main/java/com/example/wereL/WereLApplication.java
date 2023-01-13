package com.example.wereL;

import com.example.wereL.dao.CategoryRepository;
import com.example.wereL.model.entity.Category;
import com.example.wereL.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Iterator;

@SpringBootApplication
public class WereLApplication {


    public static void main(String[] args) {
        SpringApplication.run(WereLApplication.class, args);
    }



}
