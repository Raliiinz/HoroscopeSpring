package ru.itis.horoscope.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.horoscope.exception.EmailSendingException;
import ru.itis.horoscope.service.BrevoService;

import java.util.Map;

@RestController // Важно использовать @RestController, а не @Controller
public class TestController {

    private final BrevoService brevoService;
    public TestController(BrevoService brevoService) {
        this.brevoService = brevoService;
    }
    @GetMapping("/test-email")

    public ResponseEntity<?> testEmail(@RequestParam String email) throws EmailSendingException {
        System.out.println("Тестовый эндпоинт вызван для email: " + email);
        boolean result = brevoService.sendPasswordResetEmail(email, "test-token-123");
        System.out.println("Результат отправки: " + result);
        return ResponseEntity.ok().body(Map.of("success", result));
    }
}
