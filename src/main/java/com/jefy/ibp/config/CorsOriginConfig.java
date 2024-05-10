package com.jefy.ibp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.springframework.http.HttpHeaders.*;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 01/05/2024
 */
@Configuration
public class CorsOriginConfig {

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:4200","http://localhost:3000"));
        config.setAllowedHeaders(List.of(ORIGIN, CONTENT_TYPE, AUTHORIZATION, ACCEPT));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
