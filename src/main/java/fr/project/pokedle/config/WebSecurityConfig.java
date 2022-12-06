package fr.project.pokedle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.CookieRequestCache;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    private static final String[] EVERYONE_LIST_URLS = {
            "/", "/home", "/register", "/login"
    };

    private static final String[] USER_LIST_URLS = {
            "/play/classic", "/play/classic/try", "/play/classic/previous",
            "/play/splash_art", "/play/splash_art/try", "/play/splash_art/previous", "play/splash_art/partial_splash_art"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                    .antMatchers(EVERYONE_LIST_URLS)
                .permitAll()
                    .antMatchers(USER_LIST_URLS)
                        .hasAuthority("USER")
                        .anyRequest()
                .permitAll().and()
                .formLogin().loginPage("/login").permitAll().and()
                .requestCache().requestCache(new CookieRequestCache()).and()
                .logout().permitAll();
        return http.build();
    }
}
