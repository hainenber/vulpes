package io.hainenber.vulpes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class VulpesApplication {
    public static void main(String[] args) {
        SpringApplication.run(VulpesApplication.class, args);
    }

    @Value("${vulpes.web-ui.url}")
    private String vulpesWebUrl;

    // Enable Cross-Origin Resource Sharing (CORS) for localhost.
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/*")
                        .allowedOrigins(vulpesWebUrl);
            }
        };
    }
}
