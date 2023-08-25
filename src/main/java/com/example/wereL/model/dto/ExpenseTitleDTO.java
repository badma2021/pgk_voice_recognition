package com.example.wereL.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExpenseTitleDTO {
    private Long id;
    private String expenseName;
    private String categoryId;
    private String categoryName;

    public ExpenseTitleDTO(Long id, String expenseName, String categoryId, String categoryName) {
        this.id = id;
        this.expenseName = expenseName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
