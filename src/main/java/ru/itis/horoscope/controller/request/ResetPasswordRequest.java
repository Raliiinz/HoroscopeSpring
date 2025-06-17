package ru.itis.horoscope.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String newPassword;

    @NotBlank(message = "Подтверждение пароля не может быть пустым")
    private String confirmPassword;
}
