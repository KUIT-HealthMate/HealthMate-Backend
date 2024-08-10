package com.kuit.healthmate;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableBatchProcessing
public class HealthMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthMateApplication.class, args);
    }

}
