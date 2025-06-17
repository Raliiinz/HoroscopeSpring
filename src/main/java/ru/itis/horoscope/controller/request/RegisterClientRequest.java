package ru.itis.horoscope.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


//@Getter
//@Setter
//public class RegisterClientRequest {
//
//    @NotBlank
//    private String userName;
//
//    @NotBlank
//    private String birthDate;
//
//    @NotBlank
//    private String password;
//
//    @NotBlank
//    private String confirmPassword;
//
//    @NotBlank
//    @Pattern(
//            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
//            message = "Email должен быть в формате example@domain.com"
//    )
//    private String email;
//}
@Data
@Schema(description = "Запрос на регистрацию нового клиента")
public class RegisterClientRequest {

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Schema(example = "john_doe", description = "Имя пользователя")
    private String userName;

    @NotBlank(message = "Дата рождения обязательна")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Дата должна быть в формате yyyy-MM-dd")
    @Schema(example = "2000-01-01", description = "Дата рождения в формате yyyy-MM-dd")
    private String birthDate;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    @Schema(example = "password123", description = "Пароль пользователя")
    private String password;

    @NotBlank(message = "Подтверждение пароля не может быть пустым")
    @Schema(example = "password123", description = "Подтверждение пароля")
    private String confirmPassword;

    @NotBlank(message = "Email не может быть пустым")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email должен быть в формате example@domain.com"
    )
    @Schema(example = "user@example.com", description = "Email пользователя")
    private String email;
}