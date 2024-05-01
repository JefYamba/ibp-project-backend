package com.jefy.ibp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@Configuration
public class CorsOriginConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200","http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders(
                                "Content-Type", "Authorization", "Origin", "Access-Control-Allow-Origin",
                                "Accept", "Jwt-Token", "Origin, Accept", "X-Requested-With",
                                "Access-Control-Request-Method", "Access-Control-Request-Headers")
                        .exposedHeaders(
                                "Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin",
                                "Access-Control-Allow-Credentials", "Filename"
                        )
                        .allowCredentials(true);
            }
        };
    }
}
