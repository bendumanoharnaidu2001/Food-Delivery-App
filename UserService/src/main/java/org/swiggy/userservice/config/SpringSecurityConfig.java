package org.swiggy.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers(HttpMethod.POST,"/users/**").permitAll().
                            requestMatchers(HttpMethod.POST,"/users/login").permitAll().
                            requestMatchers(HttpMethod.POST,"/users/verify").permitAll().
                            requestMatchers(HttpMethod.POST,"/users/assign-delivery-partner").permitAll().
                            requestMatchers(HttpMethod.GET,"/users/userDetails/**").permitAll().
                            anyRequest().authenticated();
                }).
                headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)).httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
