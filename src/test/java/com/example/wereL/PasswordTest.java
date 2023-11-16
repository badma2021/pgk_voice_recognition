package com.example.wereL;

import com.example.wereL.config.AwsConfiguration;
import com.example.wereL.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

public class PasswordTest {
    @Autowired
    Environment env;

//    @Test
//    void getDecryptedPass() {
//    //    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
//    //    User user= new User();
//
////        String password = passwordEncoder.encode("test123");
//        System.out.println(env.getProperty("accessKey"));


    //}
}
