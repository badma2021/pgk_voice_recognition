package com.example.wereL;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WereLApplication {


    public static void main(String[] args) {
        SpringApplication.run(WereLApplication.class, args);
    }



}
