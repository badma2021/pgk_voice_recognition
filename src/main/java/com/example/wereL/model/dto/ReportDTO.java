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

    private String category;
    private Double value;

    public ReportDTO(String category, Double value) {
    }
}
