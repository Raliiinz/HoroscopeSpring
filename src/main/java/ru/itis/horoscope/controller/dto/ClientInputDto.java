package ru.itis.horoscope.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInputDto {
    @NotBlank
    private String email;

    @NotBlank
    private String userName;

    @NotNull
    private java.sql.Date birthDate;
}
