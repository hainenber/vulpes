package io.hainenber.vulpes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class VulpesApplication {
    public static void main(String[] args) {
        SpringApplication.run(VulpesApplication.class, args);
    }

    // Enable Cross-Origin Resource Sharing (CORS) for localhost.
    // TODO: implement logic to enable CORS only for production origins.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*")
                        .allowedOrigins("http://localhost:5173"); // web-ui in DEV mode
            }
        };
    }
}
