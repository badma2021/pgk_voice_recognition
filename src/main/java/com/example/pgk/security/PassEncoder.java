package com.example.pgk.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class PassEncoder {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }


}
