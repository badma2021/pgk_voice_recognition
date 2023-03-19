package com.example.wereL.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
//@RequiredArgsConstructor
@Builder
@AllArgsConstructor
//@ToString(of = {"id", "email", "username", "password"})
@ToString
@Table(name = "expense")
public class Expense implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @ToString.Exclude
    private Long id;
    public Expense() {
    }
    @ToString.Exclude
    private LocalDateTime createdAt;
    @ManyToOne
    private ExpenseTitle expenseTitle;
    private BigDecimal amount;
    private String comment;
    @ManyToOne
    @ToString.Exclude
    private User user;
    @ToString.Exclude
    private String currency;
    @ToString.Exclude
    private BigDecimal exchangeRateToRuble;
    public Expense(LocalDateTime createdAt, ExpenseTitle expenseTitle, BigDecimal amount, String comment, User user,
                   String currency, BigDecimal exchangeRateToRuble) {
        this.createdAt = createdAt;
        this.expenseTitle = expenseTitle;
        this.amount = amount;
        this.comment = comment;
        this.user = user;
        this.currency = currency;
        this.exchangeRateToRuble = exchangeRateToRuble;
    }

}
