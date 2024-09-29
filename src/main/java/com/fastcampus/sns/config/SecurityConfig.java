package com.fastcampus.sns.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("api/*/users/login", "api/*/users/register").permitAll()
                // 회원가입이나 로그인의 경우 일단 다 접근은 허용
                .requestMatchers("api/**").authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
        http.csrf(x -> x.disable()); // Disable CSRF(Cross site Request forgery) protection
        return http.build();
    }


    /* NOTE: SessionCreationPolicy.STATELESS 란?
        세션을 사용하지 않는 방식. 즉, 스프링시큐리티가 인증 정보를 서버 세션에 저장하지 않고, 각 요청마다 인증 토큰(JWT 등)을 통해
         인증을 처리 하는 구조  보통 REST API나 JWT 기반 인증을 사용할 때 세션을 유지하지 않기 위해 사용 */

    // TODO .exceptionHandling().authenticationEntryPoint() // 에외가 던저지면 특정 엔트리포인트로 가게함

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }
}
