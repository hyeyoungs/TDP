package com.cdp.tdp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://api.tildp.shop","https://www.tildp.shop","http://localhost:8080", "http://api.tildp.shop", "http://www.tildp.shop")
                .allowedMethods("POST", "PUT", "GET", "DELETE");
    }
}