package ru.itis.horoscope.controller.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.horoscope.controller.dto.ClientDto;
import ru.itis.horoscope.controller.dto.ClientOutputDto;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.service.ZodiacService;


@Component
@RequiredArgsConstructor
public class ClientMapper {
    private final ZodiacService zodiacService;

    public ClientOutputDto toOutputDto(Client client) {
        ClientOutputDto dto = new ClientOutputDto();
        dto.setEmail(client.getEmail());
        dto.setUserName(client.getUserName());
        dto.setBirthDate(client.getBirthDate());

        if (client.getBirthDate() != null) {
            try {
                dto.setZodiacSign(zodiacService.getZodiac(client.getBirthDate().toString()).getSignName());
            } catch (Exception e) {
                // Логируем ошибку, но не прерываем выполнение
//                log.warn("Could not resolve zodiac sign for birth date: {}", client.getBirthDate());
            }
        }

        return dto;
    }

    public ClientOutputDto toOutputDto(ClientDto clientDto) {
        ClientOutputDto dto = new ClientOutputDto();
        dto.setEmail(clientDto.getEmail());
        dto.setUserName(clientDto.getUserName());
        dto.setBirthDate(clientDto.getBirthDate());

        if (clientDto.getBirthDate() != null) {
            try {
                dto.setZodiacSign(zodiacService.getZodiac(clientDto.getBirthDate().toString()).getSignName());
            } catch (Exception e) {
                // Логируем ошибку, но не прерываем выполнение
//                log.warn("Could not resolve zodiac sign for birth date: {}", client.getBirthDate());
            }
        }

        return dto;
    }

}
