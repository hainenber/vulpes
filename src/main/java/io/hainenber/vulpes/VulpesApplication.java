package io.hainenber.vulpes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VulpesApplication {
    public static void main(String[] args) {
        SpringApplication.run(VulpesApplication.class, args);
    }
}
