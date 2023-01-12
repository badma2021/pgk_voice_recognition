package com.example.wereL.model.dto;

import com.example.wereL.model.entity.ExpenseTitle;
import com.example.wereL.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExpenseDTO {
    private Long id;
    @ToString.Exclude
    private LocalDateTime createdAt;
    private int expenseTitleId;
    private BigDecimal amount;
    private String comment;
    private int userId;
    private String currencyName;
    private BigDecimal exchangeRateToRuble;
}
