package ru.preproject.task_3_1_5_v2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity(debug = false)
public class WebSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {
        this.userDetailsService = userDetailsService;
        this.successUserHandler = successUserHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/index", "/js/*").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/login").anonymous()
                        .requestMatchers("/logout").authenticated()
                        .anyRequest().permitAll())
                .formLogin()
                .usernameParameter("email")
                .successHandler(successUserHandler)
                .and()
                .logout()
                .and()
                .userDetailsService(userDetailsService)
        ;
        return http.build();
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
