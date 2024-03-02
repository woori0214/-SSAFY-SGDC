package com.ssafy.sgdc.config;

import com.ssafy.sgdc.filter.JwtAuthenticationEntryPoint;
import com.ssafy.sgdc.filter.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(

                        (authorize) -> authorize
                                /////////////////// 개발 편의를 위해 추가 이후 삭제 예정
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers( "/swagger-ui/**").permitAll()
                                .requestMatchers("/api-docs/**").permitAll()
                                .requestMatchers("/api/authenticate").permitAll()
                                ///////////////////
                                .requestMatchers("/api/v1/feed/**").permitAll()
                                .requestMatchers("/api/v1/user/signup/**").permitAll()
                                .requestMatchers("/api/v1/user/login/**").permitAll()
                                .requestMatchers("/api/v1/user/re-auth/**").permitAll()
                                .anyRequest().authenticated()
//                                .anyRequest().permitAll()
                )
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint(entryPoint) //customEntryPoint
//                                .accessDeniedHandler(jwtAccessDeniedHandler) // cutomAccessDeniedHandler
                )
//                .exceptionHandling(Customizer.withDefaults())
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
