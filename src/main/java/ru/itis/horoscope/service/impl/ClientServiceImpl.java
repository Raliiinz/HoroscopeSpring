package ru.itis.horoscope.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.horoscope.controller.dto.ClientDto;
import ru.itis.horoscope.controller.dto.ClientInputDto;
import ru.itis.horoscope.controller.dto.ClientOutputDto;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.exception.ClientAlreadyExistsException;
import ru.itis.horoscope.exception.NotFoundException;
import ru.itis.horoscope.exception.PasswordMismatchException;
import ru.itis.horoscope.repository.ClientRepository;
import ru.itis.horoscope.service.ClientService;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public Client createClient(ClientInputDto clientDto, String password) {
        if (clientRepository.findByEmail(clientDto.getEmail()).isPresent()) {
            throw new ClientAlreadyExistsException(clientDto.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(password);

        Client client = new Client(
                clientDto.getEmail(),
                clientDto.getUserName(),
                clientDto.getBirthDate(),
                encodedPassword

        );
        return clientRepository.save(client);
    }

    @Transactional
    @Override
    public void updateClientById(Integer clientId, ClientOutputDto clientOutputDto) {
        Client client = clientRepository
                .findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиент с ID: " + clientId + " не найден"));

        if (!client.getEmail().equals(clientOutputDto.getEmail()) && clientRepository.findByEmail(clientOutputDto.getEmail()).isPresent()) {
            throw new ClientAlreadyExistsException(clientOutputDto.getEmail());
        }

        client.setUserName(clientOutputDto.getUserName());
        client.setBirthDate(clientOutputDto.getBirthDate());
        client.setEmail(clientOutputDto.getEmail());

        clientRepository.save(client);
    }

    @Transactional
    @Override
    public void changePassword(Integer clientId, String newPassword, String confirmNewPassword) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиент не найден"));

        if(!newPassword.equals(confirmNewPassword)) {
            throw new PasswordMismatchException("Пароли не совпадают");
        }

        client.setPassword(passwordEncoder.encode(newPassword));
        clientRepository.save(client);
    }


    @Transactional
    @Override
    public void delete(Integer clientId) {
        clientRepository.deleteById(clientId);
    }


    @Override
    public Client findClientById(Integer clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Клиент с ID: " + clientId + " не найден"));
    }

    @Override
    public Client findClientByEmail(String email) {
        System.out.println("c");
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Клиент с почтой " + email + " не найден"));

    }
}
