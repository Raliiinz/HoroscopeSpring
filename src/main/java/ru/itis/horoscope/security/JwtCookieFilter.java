package ru.itis.horoscope.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.service.ClientService;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtCookieFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;
    private final ClientService clientService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        String jwt = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        if (jwt != null) {
            try {
                String clientId = jwtTokenUtils.extractId(jwt);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                clientId,
                                null,
                                jwtTokenUtils.extractRoles(jwt).stream()
                                        .map(SimpleGrantedAuthority::new).toList()
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);

                if (request.getSession().getAttribute("client") == null) {
                    Client client = clientService.findClientById(Integer.parseInt(clientId));
                    request.getSession().setAttribute("client", client);
                }

            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                response.sendRedirect("/login");
                return;
            }
        } else {
            response.sendRedirect("/login");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return path.equals("/")
                || path.equals("/login")
                || path.equals("/reset-password")
                || path.equals("/register")
                || path.startsWith("/api/")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/assets/")
                || path.startsWith("/WEB-INF/views/");
    }
}
