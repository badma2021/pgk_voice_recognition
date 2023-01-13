package com.example.wereL.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExpenseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private int expenseTitleId;
    private BigDecimal amount;
    private String comment;
    private int userId;
    private String currencyName;
    private BigDecimal exchangeRateToRuble;
}
