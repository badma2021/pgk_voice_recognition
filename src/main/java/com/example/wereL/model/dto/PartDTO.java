package com.example.wereL.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PartDTO implements Serializable {
    private Long id;
    private String partName;
    private Long partNumber;
    private String productionYear;
    private String factoryNumber;
    private String comment;
    private String audioRecordName;
    private LocalDateTime createdAt;
    private Long userId;
}
