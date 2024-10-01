package com.fastcampus.sns.config;

import com.fastcampus.sns.service.UserService;
import com.fastcampus.sns.util.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthConfig {

    private final UserService userService;

    @Value("${jwt.secret-key}")
    private String key;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("api/*/users/login", "api/*/users/register").permitAll()
                // 회원가입이나 로그인의 경우 일단 다 접근은 허용
                .requestMatchers("api/**").authenticated()
            )
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint()))
            .addFilterBefore(new JwtTokenFilter(key, userService), UsernamePasswordAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable); // Disable CSRF(Cross site Request forgery) protection
        return http.build();
    }

    @Bean
    public BasicAuthenticationEntryPoint authenticationEntryPoint() {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("YourRealm"); // Customize as needed
        return entryPoint;
    }

    /* NOTE: SessionCreationPolicy.STATELESS 란?
        세션을 사용하지 않는 방식. 즉, 스프링시큐리티가 인증 정보를 서버 세션에 저장하지 않고, 각 요청마다 인증 토큰(JWT 등)을 통해
         인증을 처리 하는 구조  보통 REST API나 JWT 기반 인증을 사용할 때 세션을 유지하지 않기 위해 사용 */
    /* NOTE: JwtTokenFilter 역할??
        매 리퀘스트마다 토큰이 하나씩 들어있을텐데, 필터를 해서 어떤 유저를 가리키는지 알아내야 한다.
         addFilterBefore(A, B) 는 B 전에 A를 수행해야 한다는 뜻이라서, 필터링이 먼저 선행된다.*/

}
