package com.fatouchoco.servicetransport.SecurityConfig;

import com.fatouchoco.servicetransport.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfigura {

    @Autowired
    private UserService service;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(Customizer.withDefaults()) // Enable CSRF protection
                .formLogin(form -> form
                        .loginPage("/api/v1/transport/login")
                        .defaultSuccessUrl("/api/v1/transport/home", true)
                        .permitAll())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests((requests) -> {
                    requests
                            .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**", "/api/v1/transport/login", "/api/v1/transport/register").permitAll()
                            .requestMatchers("/api/v1/transport/reports", "/api/v1/transport/product", "/api/v1/transport/users").hasAnyRole("MANAGER")
                            .requestMatchers("/api/v1/transport/update-status/**").authenticated()
                            .anyRequest().authenticated();
                })
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/api/v1/transport/login?logout")
                        .permitAll()
                )


                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler()))
                .build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> response.sendRedirect("/api/v1/transport/access-denied");
    }
}
