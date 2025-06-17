package ru.itis.horoscope.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientOutputDto extends ClientInputDto {
    private String zodiacSign;
}
