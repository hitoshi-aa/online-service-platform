package com.onlineservise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("[CONFIG] Creating SINGLETON BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }
}
