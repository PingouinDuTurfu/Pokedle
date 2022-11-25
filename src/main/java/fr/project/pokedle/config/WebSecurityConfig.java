package fr.project.pokedle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    private static final String[] EVERYONE_LIST_URLS = {
            "/", "/home", "/register", "/login"
    };

    private static final String[] USER_LIST_URLS = {
            "/play/official", "/play/official_try"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                    .antMatchers(EVERYONE_LIST_URLS).permitAll()
                    .antMatchers(USER_LIST_URLS)
                        .hasAuthority("USER")
                        .anyRequest()
                        .permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
        return http.build();
    }
}
