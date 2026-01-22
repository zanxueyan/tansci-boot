package com.tansci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tansci"})
public class TansciBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TansciBootApplication.class, args);
    }

}
