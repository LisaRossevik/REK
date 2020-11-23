package com.rek.MoonPark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoonParkApplication {
    //http://localhost:8080/takst?zone=M3&start=2020-11-15T22:00:00&end=2020-11-16T02:00:00
    public static void main(String[] args) {
        SpringApplication.run(MoonParkApplication.class, args);
    }

}
