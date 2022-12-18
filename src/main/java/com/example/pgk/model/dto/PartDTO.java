package com.example.pgk.model.dto;

import com.example.pgk.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
