package ru.itis.horoscope.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.horoscope.controller.dto.ClientInputDto;
import ru.itis.horoscope.controller.dto.ClientOutputDto;
import ru.itis.horoscope.controller.mapper.ClientMapper;
import ru.itis.horoscope.controller.request.ChangePasswordRequest;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.exception.ClientAlreadyExistsException;
import ru.itis.horoscope.exception.PasswordMismatchException;
import ru.itis.horoscope.security.AuthUtils;
import ru.itis.horoscope.service.ClientService;

@Tag(name = "Profile Rest Controller", description = "Операции для работы с профилем пользователя")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileRestController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final AuthUtils authUtils;

    @Operation(summary = "Получить данные текущего пользователя",
            description = "Возвращает информацию о текущем аутентифицированном пользователе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение данных пользователя",
                    content = @Content(schema = @Schema(implementation = ClientInputDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<ClientOutputDto> getCurrentUser() {
        int clientId = authUtils.getCurrentClientId();
        Client client = clientService.findClientById(clientId);
        return ResponseEntity.ok(clientMapper.toOutputDto(client));
    }

    @Operation(summary = "Обновить данные профиля",
            description = "Позволяет обновить информацию о пользователе")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные успешно обновлены",
                    content = @Content(schema = @Schema(implementation = ClientInputDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Email уже занят другим пользователем",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/me")
    public ResponseEntity<ClientOutputDto> updateProfile(@RequestBody @Valid ClientOutputDto clientOutputDto) {
        int clientId = authUtils.getCurrentClientId();
        clientService.updateClientById(clientId, clientOutputDto);
        Client updatedClient = clientService.findClientById(clientId);
        return ResponseEntity.ok(clientMapper.toOutputDto(updatedClient));
    }

    @Operation(summary = "Изменить пароль",
            description = "Позволяет изменить пароль текущего пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пароль успешно изменен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные (не совпадают пароли)",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        int clientId = authUtils.getCurrentClientId();
        clientService.changePassword(clientId, request.getNewPassword(), request.getConfirmNewPassword());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить аккаунт",
            description = "Позволяет удалить аккаунт текущего пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Аккаунт успешно удален"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteAccount() {
        int clientId = authUtils.getCurrentClientId();
        clientService.delete(clientId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleClientAlreadyExistsException(ClientAlreadyExistsException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePasswordMismatchException(PasswordMismatchException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @Schema(description = "Error response")
    public record ErrorResponse(
            @Schema(description = "HTTP status code", example = "404")
            int status,

            @Schema(description = "Error message", example = "Пользователь не найден")
            String message
    ) {}
}
