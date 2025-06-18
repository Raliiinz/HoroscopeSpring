package ru.itis.horoscope.service;

import ru.itis.horoscope.controller.dto.ClientInputDto;
import ru.itis.horoscope.controller.dto.ClientOutputDto;
import ru.itis.horoscope.entity.Client;

public interface ClientService {
    Client createClient(ClientInputDto clientInputDto, String password);
    void updateClientById(Integer clientId, ClientOutputDto clientOutputDto);
    void changePassword(Integer clientId, String newPassword, String confirmNewPassword);
    void delete(Integer userId);
    Client findClientById(Integer clientId);
    Client findClientByEmail(String email);
}
