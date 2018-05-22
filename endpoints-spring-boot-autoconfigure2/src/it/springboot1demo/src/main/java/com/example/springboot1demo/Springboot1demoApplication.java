package com.example.springboot1demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springboot1demoApplication {

	public static void main(String[] args) {
        try {
            SpringApplication.run(Springboot1demoApplication.class, args);
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(-1);
        } finally {
            System.exit(0);
        }
	}
}
