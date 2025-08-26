package com.noithat.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Cho tất cả URL
                .allowedOrigins("http://localhost:4200") // Cho phép FE truy cập
                .allowedMethods("*") // GET, POST, PUT, DELETE,...
                .allowedHeaders("*");
    }
}
