package ru.itis.horoscope.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.horoscope.controller.dto.ClientInputDto;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.exception.*;
import ru.itis.horoscope.repository.ClientRepository;
import ru.itis.horoscope.service.ClientAuthService;
import ru.itis.horoscope.service.ClientService;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientAuthServiceImpl implements ClientAuthService {
    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;

    @Override
    public Client login(String email, String password) {
        try {
            Client client = clientService.findClientByEmail(email);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            return client;
        } catch (NotFoundException | BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public Client register(String email, String username, String password, String confirmPassword, Date birthDate) {
        if(!password.equals(confirmPassword)) {
            throw new PasswordMismatchException("Пароли не совпадают");
        }

        ClientInputDto inputDto = new ClientInputDto();
        inputDto.setEmail(email);
        inputDto.setUserName(username);
        inputDto.setBirthDate(birthDate);

        return clientService.createClient(inputDto, password);
    }


    @Override
    public String generatePasswordResetToken(String email) throws ClientNotFoundException {
        log.info("Generating token for email: {}", email);
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Client not found: {}", email);
                    return new ClientNotFoundException("Пользователь не найден");
                });

        String token = UUID.randomUUID().toString();
        log.info("Generated token: {} for email: {}", token, email);

        client.setResetToken(token);
        client.setResetTokenExpiry(LocalDateTime.now().plusHours(24));
        clientRepository.save(client);

        return token;
    }

    @Override
    public void validatePasswordResetToken(String token) throws InvalidTokenException {
        // Проверяем существование токена
        log.info("Validating token: {}", token);
        Client client = clientRepository.findByResetToken(token)
                .orElseThrow(() -> {
                    log.error("Token not found: {}", token);
                    return new InvalidTokenException("Недействительный токен");
                });

        // Проверяем срок действия
        if (client.getResetTokenExpiry() == null ||
                client.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            log.error("Token expired: {}", token);
            throw new InvalidTokenException("Срок действия токена истек");
        }
        log.info("Token validated successfully: {}", token);
    }

    @Override
    public void resetPassword(String token, String newPassword) throws InvalidTokenException {
        // Находим пользователя по токену
        Client client = clientRepository.findByResetToken(token)
                .orElseThrow(() -> new InvalidTokenException("Недействительный токен"));

        if (client.getResetTokenExpiry() == null ||
                client.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Срок действия токена истек");
        }

        // Обновляем пароль
        client.setPassword(passwordEncoder.encode(newPassword));
        // Очищаем токен
        client.setResetToken(null);
        client.setResetTokenExpiry(null);
        clientRepository.save(client);
    }
}
