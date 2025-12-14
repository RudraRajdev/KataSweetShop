package com.KataSweetShop.Main.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity

public class Config {
    private final JwtAuthenticationFilter jwtFilter;

    public Config(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UnsupportedOperationException(
                    "UserDetailsService is not used. JWT authentication only."
            );
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity https)throws Exception{

    https
            .csrf(csrf-> csrf.disable())
            .authorizeHttpRequests(auth->auth
                    // Public
                    .requestMatchers("/api/auth/**").permitAll()

                    // USER + ADMIN (
                    .requestMatchers(HttpMethod.GET, "/api/sweets/**").authenticated()

                    // ADMIN only
                    .requestMatchers(HttpMethod.POST, "/api/sweets").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/sweets/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/sweets/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/sweets/*/restock").hasRole("ADMIN")

                    .anyRequest().authenticated()

            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return https.build();

    }


}
