package com.example.wereL.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//@RequiredArgsConstructor
@Builder
@AllArgsConstructor
//@ToString(of = {"id", "email", "username", "password"})
@ToString
@Table(name = "expense")
public class Expense {
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
}
