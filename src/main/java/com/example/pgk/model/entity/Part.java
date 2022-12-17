package com.example.pgk.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//@RequiredArgsConstructor
@Builder
@AllArgsConstructor
//@ToString(of = {"id", "email", "username", "password"})
@ToString
@Table(name = "part")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @ToString.Exclude
    private Long id;

    @Column(name = "partname", unique = true)
    @ToString.Exclude
    private String partName;

    @Column
    @ToString.Exclude
    private Long partNumber;
    @ToString.Exclude
    private String productionYear;
    @ToString.Exclude
    private String factoryNumber;

    @ToString.Exclude
    private String comment;

    @ToString.Exclude
    private String audioRecordPath;
    @ToString.Exclude
    private LocalDateTime createdAt;


    @ManyToOne
    @ToString.Exclude
    private User user;
    public Part() {
    }

    public Part(String partName, Long partNumber, String productionYear, String factoryNumber, String comment,
                String audioRecordPath, LocalDateTime createdAt, User user) {
        this.partName = partName;
        this.partNumber = partNumber;
        this.productionYear = productionYear;
        this.factoryNumber = factoryNumber;
        this.comment = comment;
        this.audioRecordPath = audioRecordPath;
        this.createdAt = createdAt;
        this.user = user;
    }
}
