package com.example.spring_telecom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for APIs
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/users/**").permitAll()  // Allow public access to user endpoints
                        .requestMatchers("/api/srvces/**").permitAll()  // Allow public access to services endpoints
                        .anyRequest().authenticated()  // All other endpoints require authentication
                )
                .formLogin(form -> form.disable())  // Disable form login for APIs
                .httpBasic(basic -> basic.disable());  // Disable basic authentication

        return http.build();
    }
}