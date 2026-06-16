package com.sisyphus.backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.sisyphus.backend.security.jwt.JwtTokenProvider.HEADER_STRING;
import static com.sisyphus.backend.security.jwt.JwtTokenProvider.TOKEN_PREFIX;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1) Authorization 헤더에서 JWT 추출
        String token = resolveToken(request);

        // 2) 토큰이 있고 유효하면 Authentication 생성 후 SecurityContext에 주입
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            // (중요) 여기 authentication.getPrincipal() 이 UserPrincipal 이어야
            // 컨트롤러에서 @AuthenticationPrincipal UserPrincipal 로 받을 수 있음.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 3) 다음 필터 진행
        filterChain.doFilter(request, response);
    }

    /**
     * Authorization: Bearer <token>
     *
     * @param request HttpServletRequest
     * @return token 문자열 또는 null
     */
    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(HEADER_STRING);

        if (!StringUtils.hasText(bearer)) return null;

        // "Bearer " 로 시작하면 토큰 부분만 추출
        if (bearer.startsWith(TOKEN_PREFIX)) {
            // TOKEN_PREFIX 가 "Bearer " 라면 길이는 7
            return bearer.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}
