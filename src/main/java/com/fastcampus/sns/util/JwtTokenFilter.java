package com.fastcampus.sns.util;

import com.fastcampus.sns.dto.UserDto;
import com.fastcampus.sns.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter { // 매 요청때마다 필터링을 해야함

    private final String key;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // 헤더 꺼내기
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            log.error("Error occurs while getting header. Header is either null / invalid.\n");
            filterChain.doFilter(request, response);
            return;
        }
        // 헤더에서 토큰 파싱하기
        try {
            final String token = header.split(" ")[1].trim(); //Bearer 뒤에 스페이스 있음 고려
            // 토큰이 만료되지 않았는지 확인
            if (JwtTokenUtils.isExpired(token, key)) {
                log.error("Key is expired");
                filterChain.doFilter(request, response);
                return;
            }

            // 토큰을 사용하여 유저 정보를 가져온뒤에, 실제 존재하는 지 검증함.
            String username = JwtTokenUtils.getUsername(token, key);
            UserDto userDto = userService.loadUserDtoByUsername(username);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDto, null, userDto.getAuthorities()
            );
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (RuntimeException e) {
            log.error("Error occurs while validation. {}", e.toString());
        }

        filterChain.doFilter(request, response);
    }
}
