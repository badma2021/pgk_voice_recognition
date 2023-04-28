package com.example.wereL.model.dto;

import java.io.Serializable;

public interface ExcelDTO extends Serializable {

    String getDate();
    String getExpenseName();
    Double getValue();
    String getCategoryName();
    String getComment();
    String getCurrency();
    Double getExchangeRateToRuble();

}
