package com.example.pgk.model.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//@RequiredArgsConstructor
@Builder
@AllArgsConstructor
//@ToString(of = {"id", "email", "username", "password"})
@ToString
@Table(name = "users")
public class User /*implements UserDetails */{

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



    public User() {

    }


    /*@ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }*/
}
