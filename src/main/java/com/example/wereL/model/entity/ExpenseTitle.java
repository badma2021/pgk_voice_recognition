package com.example.wereL.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
//@RequiredArgsConstructor
@Builder
@AllArgsConstructor
//@ToString(of = {"id", "email", "username", "password"})
@ToString
@Table(name = "expense_title")
public class ExpenseTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @ToString.Exclude
    private Long id;
    private String expenseName;
    @ManyToOne
    private Category category;

    public ExpenseTitle() {

    }
}
