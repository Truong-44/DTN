//package com.example.be.TempoTide.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@Configuration
//@EnableWebSecurity
//public class SencurityConfig {
//    @Bean
//    public SencurityFileChain sencurityFileChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/**").authenticated()
//                .and()
//                .httpBasic();
//        return http.build();
//    }
//
//}
