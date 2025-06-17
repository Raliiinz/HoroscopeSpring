package ru.itis.horoscope.service;

import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.exception.ClientNotFoundException;
import ru.itis.horoscope.exception.InvalidTokenException;
import ru.itis.horoscope.exception.TooManyResetAttemptsException;

public interface ClientAuthService extends AuthService <Client> {
    String generatePasswordResetToken(String email)
            throws ClientNotFoundException, TooManyResetAttemptsException;

    void validatePasswordResetToken(String token) throws InvalidTokenException;

    void resetPassword(String token, String newPassword)
            throws InvalidTokenException;
}
