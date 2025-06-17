package ru.itis.horoscope.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    @NotBlank
    private String email;

    @NotBlank
    private String userName;

    @NotBlank
    private java.sql.Date birthDate;
}
