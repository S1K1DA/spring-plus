package org.example.expert.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String url = request.getRequestURI();

        // 로그인/회원가입 경로는 JWT 검사 없이 통과
        if (url.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization 헤더에서 Bearer 토큰 가져오기
        String bearerJwt = request.getHeader("Authorization");

        if (bearerJwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = jwtUtil.substringToken(bearerJwt);

            // JWT 검증 및 Claims 추출
            Claims claims = jwtUtil.extractClaims(jwt);

            UserRole userRole = UserRole.valueOf(claims.get("userRole", String.class));

            // request에 사용자 정보 저장
            request.setAttribute("userId", Long.parseLong(claims.getSubject()));
            request.setAttribute("email", claims.get("email"));
            request.setAttribute("userRole", claims.get("userRole"));
            request.setAttribute("nickname", claims.get("nickname"));

            // Security 인증 세팅 추가
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()))
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 토큰입니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}