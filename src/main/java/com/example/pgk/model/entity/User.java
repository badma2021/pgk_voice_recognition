package com.example.pgk.model.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
//@RequiredArgsConstructor
@Builder
@AllArgsConstructor
//@ToString(of = {"id", "email", "username", "password"})
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    @ToString.Exclude
    private Long id;

    @Column(name = "email", unique = true)
    @ToString.Exclude
    private String email;

    @Column
    @ToString.Exclude
    private String username;
    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String phone;
    @ToString.Exclude
    private String firstName;
    @ToString.Exclude
    private String lastName;

    private LocalDate birthday;
    @ToString.Exclude
    private LocalDateTime createdAt;
    @ToString.Exclude
    private LocalDateTime lastModified;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_and_role",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(String email, String password, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
