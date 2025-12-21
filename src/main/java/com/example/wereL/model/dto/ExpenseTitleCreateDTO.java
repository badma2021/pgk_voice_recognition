package com.example.wereL.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExpenseTitleCreateDTO {
    private String expenseName;
    private Long categoryId;


    public ExpenseTitleCreateDTO( String expenseName, Long categoryId) {

        this.expenseName = expenseName;
        this.categoryId = categoryId;

    }
}
