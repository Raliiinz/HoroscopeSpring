package ru.itis.horoscope.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.itis.horoscope.controller.dto.ClientInputDto;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.exception.InvalidCredentialsException;
import ru.itis.horoscope.exception.NotFoundException;
import ru.itis.horoscope.exception.PasswordMismatchException;
import ru.itis.horoscope.repository.ClientRepository;
import ru.itis.horoscope.service.ClientAuthService;
import ru.itis.horoscope.service.ClientService;

import java.sql.Date;


@Service
@RequiredArgsConstructor
public class ClientAuthServiceImpl implements ClientAuthService {
    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;

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
//        Client client = clientService.createClient(
//                new ClientDto(email, username, birthDate), password);
//        return clientService.findClientByEmail(client.getEmail());
    }
}
