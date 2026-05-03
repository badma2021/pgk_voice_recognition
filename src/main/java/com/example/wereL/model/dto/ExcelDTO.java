package com.example.wereL.model.dto;

import java.io.Serializable;

public interface ExcelDTO extends Serializable {

    String getDate();
    Integer getExpenseId();
    String getExpenseName();
    Double getValue();
    Integer getCategoryId();
    String getCategoryName();
    String getComment();
    String getCurrency();
    Double getExchangeRateToRuble();

}
