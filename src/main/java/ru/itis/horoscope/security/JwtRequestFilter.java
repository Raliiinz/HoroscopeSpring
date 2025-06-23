package ru.itis.horoscope.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        extractJwtFromHeader(request.getHeader("Authorization"))
                .ifPresent(jwt -> authenticateIfNotAuthenticated(jwt));

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractJwtFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith(AUTH_HEADER_PREFIX)) {
            return Optional.empty();
        }
        return Optional.of(authHeader.substring(AUTH_HEADER_PREFIX.length()));
    }

    private void authenticateIfNotAuthenticated(String jwt) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        try {
            String id = jwtTokenUtils.extractId(jwt);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    id,
                    null,
                    jwtTokenUtils.extractRoles(jwt).stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList()
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Не удалось аутентифицировать пользователя по JWT", e);
        }
    }
}
