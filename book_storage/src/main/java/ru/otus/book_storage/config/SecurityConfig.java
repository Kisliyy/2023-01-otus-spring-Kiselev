package ru.otus.book_storage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .antMatchers("/actuator/**").hasAnyRole("ADMIN")
                                .antMatchers("/webjars/**").permitAll()
                                .antMatchers("/", "/add", "/edit").hasAnyRole("ADMIN", "USER")
                                .antMatchers("/authors").hasAnyRole("ADMIN", "USER")
                                .antMatchers("/genres").hasAnyRole("ADMIN", "USER")
                                .antMatchers(HttpMethod.GET, "/books/**").hasAnyRole("ADMIN", "USER")
                                .antMatchers(HttpMethod.DELETE, "/books/**").hasAnyRole("USER")
                                .antMatchers(HttpMethod.PUT, "/books/**").hasAnyRole("USER")
                                .antMatchers(HttpMethod.POST, "/books/**").hasAnyRole("USER")
                                .anyRequest().denyAll()
                )
                .formLogin().permitAll();
        return http
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
