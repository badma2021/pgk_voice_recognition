package com.example.wereL;

import com.example.wereL.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {


    @Test
    void getDecryptedPass() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
    //    User user= new User();

        String password = passwordEncoder.encode("test123");
        System.out.println(password);
    }
}
