package com.example.wereL.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CategoryDTO {
    private Long id;

    private String categoryName;

    public CategoryDTO(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
