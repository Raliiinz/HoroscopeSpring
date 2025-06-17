package ru.itis.horoscope.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на смену пароля")
public class ChangePasswordRequest {

    @NotBlank(message = "Новый пароль не может быть пустым")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    @Schema(example = "newPassword123", description = "Новый пароль")
    private String newPassword;


    @NotBlank(message = "Подтверждение нового пароля не может быть пустым")
    @Schema(example = "newPassword123", description = "Подтверждение нового пароля")
    private String confirmNewPassword;
}
