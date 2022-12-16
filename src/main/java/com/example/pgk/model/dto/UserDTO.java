package com.example.pgk.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    private int id;
    private String email;

    private String password;
    private RoleDTO[] roles;
    private String firstName;
    private String lastName;
    private String avatar;
    private String status;

    private LocalDate birthday;
    private int user_id;
    private int project_id;

    public UserDTO(String email, RoleDTO[] roles) {
        this.email = email;
        this.roles = roles;
    }
}
