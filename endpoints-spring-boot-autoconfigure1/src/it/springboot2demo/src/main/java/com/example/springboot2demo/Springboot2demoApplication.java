package com.example.springboot2demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springboot2demoApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(Springboot2demoApplication.class, args);
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            System.exit(0);
        }

    }
}
