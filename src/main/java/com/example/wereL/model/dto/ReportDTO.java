package com.example.wereL.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReportDTO {
    private String year;
    private String month;
    private String category;
    private Double value;

    public ReportDTO(String year, String month, String category, Double value) {
    }
}
