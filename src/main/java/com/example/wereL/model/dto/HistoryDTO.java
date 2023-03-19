package com.example.wereL.model.dto;

import lombok.*;

import javax.persistence.Entity;



public interface HistoryDTO {
    String getId();
    String getDate();
    String getExpenseName();
    Double getValue();


}
