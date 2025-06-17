package ru.itis.horoscope.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Schema(description = "Запрос на аутентификацию клиента")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginClientRequest {

    @NotBlank(message = "Email не может быть пустым")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email должен быть в формате example@domain.com"
    )
    @Schema(example = "user@example.com", description = "Email пользователя")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Schema(example = "password123", description = "Пароль пользователя")
    private String password;
}
