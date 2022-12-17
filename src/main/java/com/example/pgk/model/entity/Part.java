package com.example.pgk.model.entity;


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
@Table(name = "audiorecord")
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
    private int factoryNumber;

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
}
