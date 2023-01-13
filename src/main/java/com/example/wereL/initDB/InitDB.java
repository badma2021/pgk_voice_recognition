package com.example.wereL.initDB;

import com.example.wereL.dao.*;
import com.example.wereL.model.entity.*;
import com.example.wereL.security.PassEncoder;
import com.example.wereL.repository.UserRepositoryImpl;
import com.example.wereL.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;

@Component
public class InitDB {
    private final UserRepositoryImpl userRepository;
    private final RoleRepositoryJpql roleRepositoryJpql;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    //private final CurrencyRepository currencyRepository;
    private final ExpenseTitleRepository expenseTitleRepository;
    private final ExcelUtil excelUtil;
    private final PassEncoder passwordEncoder;

    @Autowired
    public InitDB(UserRepositoryImpl userRepository,
                  RoleRepositoryJpql roleRepositoryJpql, CategoryRepository categoryRepository,
                  ExpenseRepository expenseRepository, ExpenseTitleRepository expenseTitleRepository, ExcelUtil excelUtil, PassEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepositoryJpql = roleRepositoryJpql;
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
        // this.currencyRepository = currencyRepository;
        this.expenseTitleRepository = expenseTitleRepository;
        this.excelUtil = excelUtil;
        this.passwordEncoder = passwordEncoder;

    }

    @PostConstruct
    public void initializationDB() throws IOException {

//        User user1 = User.builder()
//                .firstName("Ivan")
//                .lastName("Ivanov")
//                .phone("777")
//                .email("ivan@mail.ru")
//                .password(passwordEncoder.passwordEncoder().encode("Ivan080navI"))
//                .username("ivan")
//                .enabled(true)
//                .roles(Set.of(new Role("ROLE_USER")))
//                .build();
//        User user2 = User.builder()
//                .firstName("Roman")
//                .lastName("Romanov")
//                .phone("773")
//                .email("roman@mail.ru")
//                .password(passwordEncoder.passwordEncoder().encode("2"))
//                .username("roman")
//                .enabled(true)
//                .roles(Set.of(new Role("ROLE_ADMIN")))
//                .build();
//        User user3 = User.builder()
//                .firstName("Marina")
//                .lastName("Fedotova")
//                .phone("774")
//                .email("marina@mail.ru")
//                .password(passwordEncoder.passwordEncoder().encode("5"))
//                .username("marina")
//                .enabled(true)
//                .build();

//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);
///////////////////////////////////////////////////////////
//        Currency cur1= Currency.builder()
//                .currencyName("RUB")
//                .build();
//        Currency cur2= Currency.builder()
//                .currencyName("RSD")
//                .createdAt(LocalDateTime.of(2023, 1, 12, 13, 30))
//                .exchangeRateToRuble(new BigDecimal(0.64))
//                .build();
//        Currency cur3= Currency.builder()
//                .currencyName("EUR")
//                .build();
//        Currency cur4= Currency.builder()
//                .currencyName("USD")
//                .build();
//        currencyRepository.save(cur1);
//        currencyRepository.save(cur2);
//        currencyRepository.save(cur3);
//        currencyRepository.save(cur4);
        ///////////////////////////////////////////////////////////
//        ExpenseTitle expt1 = ExpenseTitle.builder()
//                .expenseName("bread")
//                .category(cat1)
//                .build();
//        ExpenseTitle expt2 = ExpenseTitle.builder()
//                .expenseName("milk")
//                .category(cat1)
//                .build();
//        ExpenseTitle expt3 = ExpenseTitle.builder()
//                .expenseName("toothpaste")
//                .category(cat2)
//                .build();
//        expenseTitleRepository.save(expt1);
//        expenseTitleRepository.save(expt2);
//        expenseTitleRepository.save(expt3);
/////////////////////////////////////////////////////////////////
//        Expense exp1 = Expense.builder()
//                .amount(new BigDecimal(40.50))
//                .expenseTitle(expt1)
//                .createdAt(LocalDateTime.of(2023, 1, 12, 13, 30))
//                .comment("")
//                .user(user1)
//                .currency("RSD")
//                .exchangeRateToRuble(new BigDecimal(0.64))
//                .build();
//        Expense exp2 = Expense.builder()
//                .amount(new BigDecimal(90.50))
//                .expenseTitle(expt2)
//                .createdAt(LocalDateTime.of(2023, 1, 12, 13, 30))
//                .comment("")
//                .user(user1)
//                .currency("RSD")
//                .exchangeRateToRuble(new BigDecimal(0.64))
//                .build();
//        Expense exp3 = Expense.builder()
//                .amount(new BigDecimal(240.50))
//                .expenseTitle(expt3)
//                .createdAt(LocalDateTime.of(2023, 1, 12, 13, 30))
//                .comment("")
//                .user(user1)
//                .currency("RSD")
//                .exchangeRateToRuble(new BigDecimal(0.64))
//                .build();
//        expenseRepository.save(exp1);
//        expenseRepository.save(exp2);
//        expenseRepository.save(exp3);
//init1();
        }
//    public void init1() throws IOException {
//        XSSFSheet sheet = excelUtil.readExcel("src/main/resources/excel/category.xlsx", "Лист1");
//        Iterator<Row> rowIterator = sheet.iterator();
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            String desc = row.getCell(1).getStringCellValue();
//            Category cat = Category.builder()
//                    .categoryName(desc)
//                    .build();
//            categoryRepository.save(cat);
//        }}
    }
