package com.em.tms.config;

import com.em.tms.security.JwtAuthenticationFilter;
import com.em.tms.security.JwtTokenProvider;
import com.em.tms.security.JwtUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUserDetailsService jwtUserDetailsService;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, JwtUserDetailsService jwtUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz

                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/register", "/api/users/login", "/api/auth/refresh").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/users/{email}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/{email}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/{email}").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/tasks/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/tasks/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/tasks/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/tasks/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/comments").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/comments/task/{taskId}").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/comments/{commentId}").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, jwtUserDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
}
