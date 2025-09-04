package com.example.MedicNote.config;

import com.example.MedicNote.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/auth/login",
                    "/api/auth/register",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/v3/api-docs.yaml",
                    "/api/appointments/test/**"
                ).permitAll()
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                .requestMatchers("/api/doctors/public").permitAll()
                .requestMatchers("/api/doctors").hasAnyRole("ADMIN", "DOCTOR", "PATIENT", "RECEPTIONIST")
                .requestMatchers("/api/doctors/{id}").hasAnyRole("ADMIN", "DOCTOR", "PATIENT", "RECEPTIONIST")
                .requestMatchers("/api/doctors/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST")
                .requestMatchers("/api/patients/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST")
                // Allow patients to view available slots and slots, and view/create their appointments
                .requestMatchers(HttpMethod.GET, "/api/available-slots/**").hasAnyRole("ADMIN", "DOCTOR", "PATIENT")
                .requestMatchers(HttpMethod.GET, "/api/appointments/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST", "PATIENT")
                .requestMatchers(HttpMethod.POST, "/api/appointments/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST", "PATIENT")
                .requestMatchers(HttpMethod.GET, "/api/slots/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST", "PATIENT")
                // Non-GET access remains restricted
                .requestMatchers("/api/available-slots/**").hasAnyRole("ADMIN", "DOCTOR")
                .requestMatchers("/api/appointments/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST")
                .requestMatchers("/api/slots/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST")
                .anyRequest().authenticated()
            );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
} 