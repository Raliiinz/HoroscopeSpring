package ru.itis.horoscope.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.horoscope.controller.request.LoginClientRequest;
import ru.itis.horoscope.controller.request.RegisterClientRequest;
import ru.itis.horoscope.controller.response.JwtResponse;
import ru.itis.horoscope.entity.Client;
import ru.itis.horoscope.exception.ClientAlreadyExistsException;
import ru.itis.horoscope.exception.InvalidCredentialsException;
import ru.itis.horoscope.security.JwtTokenUtils;
import ru.itis.horoscope.service.ClientAuthService;

import java.sql.Date;

@Tag(name = "Auth Rest Controller", description = "Аутентификация и регистрация пользователей")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final ClientAuthService clientAuthService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @Operation(summary = "Аутентификация пользователя", description = "Возвращает JWT токен для авторизованных запросов")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная аутентификация",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginClientRequest loginRequest) {
        try {
            Client client = clientAuthService.login(loginRequest.getEmail(), loginRequest.getPassword());
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            String token = jwtTokenUtils.generateToken(client.getId(), userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (InvalidCredentialsException e) {
            throw new InvalidCredentialsException();
        }
    }

    @Operation(summary = "Регистрация нового пользователя", description = "Создает нового пользователя и возвращает JWT токен")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная регистрация",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Пользователь уже существует",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterClientRequest registerRequest) {
        try {
            Client client = clientAuthService.register(
                    registerRequest.getEmail(),
                    registerRequest.getUserName(),
                    registerRequest.getPassword(),
                    registerRequest.getConfirmPassword(),
                    Date.valueOf(registerRequest.getBirthDate())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(registerRequest.getEmail());
            String token = jwtTokenUtils.generateToken(client.getId(), userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (ClientAlreadyExistsException e) {
            throw new ClientAlreadyExistsException("Пользователь с таким email уже существует");
        }
    }
}
