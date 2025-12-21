package com.example.wereL.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExpenseTitleEditDTO {
    private Long id;
    private String expenseName;
}
