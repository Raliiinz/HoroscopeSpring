package ru.itis.horoscope.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.itis.horoscope.exception.UnauthorizedException;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final JwtTokenUtils jwtTokenUtils;

    public Integer getCurrentClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Пользователь не аутентифицирован");
        }

        String clientId = authentication.getName();
        return Integer.valueOf(clientId);
    }
}
