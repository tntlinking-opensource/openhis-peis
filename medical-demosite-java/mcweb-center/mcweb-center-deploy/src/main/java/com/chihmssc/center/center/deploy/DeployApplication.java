package com.center.medical.center.deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class DeployApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeployApplication.class, args);
    }

}
