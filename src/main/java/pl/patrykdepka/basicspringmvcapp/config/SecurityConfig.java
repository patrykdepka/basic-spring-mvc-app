package pl.patrykdepka.basicspringmvcapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import pl.patrykdepka.basicspringmvcapp.appuser.CustomAuthenticationFailureHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .mvcMatchers("/login**").permitAll()
                .anyRequest().authenticated());
        http.formLogin(login -> login
                .loginPage("/login").permitAll()
                .failureHandler(new CustomAuthenticationFailureHandler())
        );
        http.csrf().disable();
        http.headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().mvcMatchers(
                "/styles/**"
        );
    }
}
